package mx.gob.babien.tcb.controller;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.babien.tcb.catalog.CountryCatalog;
import mx.gob.babien.tcb.catalog.FiscalRegimeCatalog;
import mx.gob.babien.tcb.exception.BadRequestException;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.model.CurpData;
import mx.gob.babien.tcb.service.AltaClienteService;
import mx.gob.babien.tcb.service.CatalogoGeneralService;
import mx.gob.babien.tcb.service.ClienteBusquedaService;
import mx.gob.babien.tcb.service.CodigoPostalService;
import mx.gob.babien.tcb.util.CurpUtils;
import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.request.CodigoPostalRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralResponseDto;
import mx.gob.babien.tcb.dto.response.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.dto.response.CodigoPostalResponseDto;
import mx.gob.babien.tcb.dto.response.CurpDatosResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OnboardingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnboardingController.class);
    private static final String ONBOARDING_BASE_PATH = "/onboarding";
    private static final int TOTAL_STEPS = 5;
    private static final int FIRST_STEP = 1;
    private static final int DOCUMENTS_STEP = 3;
    private static final int CONFIRMATION_STEP = 4;
    private static final String DEFAULT_DOM_REG_OCUPACION = "1";
    private static final String BLANK_VALUE = " ";
    // Generic message to avoid leaking integration/backend details to end users.
    private static final String GENERIC_INTEGRATION_ERROR = "No fue posible completar la operación en este momento.";
    private static final String REDIRECT_ONBOARDING_FIRST_STEP = "redirect:" + ONBOARDING_BASE_PATH + "/1";
    private static final String REDIRECT_DOCUMENTS_STEP = "redirect:" + ONBOARDING_BASE_PATH + "/" + DOCUMENTS_STEP;
    private static final String VIEW_ONBOARDING = "onboarding";
    private static final List<String> STEP_LABELS = List.of(
            "Datos Cliente",
            "PLD",
            "Documentos",
            "Perfil",
            "Imprimir"
    );

    private final CurpUtils curpUtils;
    private final AltaClienteService altaClienteService;
    private final ClienteBusquedaService clienteBusquedaService;
    private final CatalogoGeneralService catalogoGeneralService;
    private final CodigoPostalService codigoPostalService;

    public OnboardingController(
            CurpUtils curpUtils,
            AltaClienteService altaClienteService,
            ClienteBusquedaService clienteBusquedaService,
            CatalogoGeneralService catalogoGeneralService,
            CodigoPostalService codigoPostalService
    ) {
        this.curpUtils = curpUtils;
        this.altaClienteService = altaClienteService;
        this.clienteBusquedaService = clienteBusquedaService;
        this.catalogoGeneralService = catalogoGeneralService;
        this.codigoPostalService = codigoPostalService;
    }

    @GetMapping("/")
    public String home() {
        return REDIRECT_ONBOARDING_FIRST_STEP;
    }

    @GetMapping(ONBOARDING_BASE_PATH + "/{step}")
    public String onboarding(
            @PathVariable int step,
            @RequestParam(name = "curp", required = false) String curp,
            Model model
    ) {
        if (isStepOutOfRange(step)) {
            return REDIRECT_ONBOARDING_FIRST_STEP;
        }

        addOnboardingModelAttributes(step, model);
        addCurpModelAttributes(curp, model);
        model.addAttribute("cliente", buildClienteForOnboarding());
        return VIEW_ONBOARDING;
    }

    @GetMapping(ONBOARDING_BASE_PATH + "/documentos")
    public String documentos() {
        return REDIRECT_DOCUMENTS_STEP;
    }

    @ResponseBody
    @GetMapping(ONBOARDING_BASE_PATH + "/curp-datos")
    public ResponseEntity<CurpDatosResponseDto> curpDatos(@RequestParam(name = "curp") String curp) {
        CurpData curpData = curpUtils.extractCurpData(curp);
        CurpDatosResponseDto response = new CurpDatosResponseDto();
        response.setCurp(curpData.curp());
        response.setGenero(curpData.genero());
        response.setFechaNacimiento(curpData.fechaNacimiento());
        response.setEntidadFederativa(curpData.entidadFederativa());
        response.setPais(curpData.pais());
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(
            value = ONBOARDING_BASE_PATH + "/altacliente",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AltaClienteResponseDto> altaClienteApiSubmit(@Valid @RequestBody AltaClienteRequestDto request) {
        return ResponseEntity.ok(altaClienteService.altaCliente(request));
    }

    @ResponseBody
    @PostMapping(
            value = ONBOARDING_BASE_PATH + "/buscar-cliente",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ClienteBusquedaResponseDto> buscarCliente(@RequestBody ClienteBusquedaRequestDto request) {
        return ResponseEntity.ok(clienteBusquedaService.buscarCliente(request));
    }

    @ResponseBody
    @PostMapping(
            value = ONBOARDING_BASE_PATH + "/catalogos/general",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CatalogoGeneralResponseDto> consumirCatalogoGeneral(@RequestBody CatalogoGeneralRequestDto request) {
        return ResponseEntity.ok(catalogoGeneralService.consumirCatalogo(request));
    }

    @ResponseBody
    @PostMapping(
            value = ONBOARDING_BASE_PATH + "/codigos-postal",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CodigoPostalResponseDto> consumirCodigoPostal(@RequestBody CodigoPostalRequestDto request) {
        return ResponseEntity.ok(codigoPostalService.consumirCodigoPostal(request));
    }

    @PostMapping(ONBOARDING_BASE_PATH + "/altacliente")
    public String altaClienteFormSubmit(
            @Valid @ModelAttribute AltaClienteRequestDto request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest,
            RedirectAttributes redirectAttributes
    ) {
        Set<String> fuentesIngreso = resolveMultiValues(httpRequest, "fuenteIngresos");
        Set<String> usosCuenta = resolveMultiValues(httpRequest, "usoCuenta");
        request.setFteingrSueldos(fuentesIngreso.contains("sueldos") ? "S" : " ");
        request.setFteingrHonorarios(fuentesIngreso.contains("honorarios") ? "S" : " ");
        request.setFteingrActvComercial(fuentesIngreso.contains("actividad_comercial") ? "S" : " ");
        request.setFteingrArrendamto(fuentesIngreso.contains("arrendamiento") ? "S" : " ");
        request.setFteingrOtros(fuentesIngreso.contains("otros") ? "S" : " ");
        request.setUsoctaAdmIngreYGasts(usosCuenta.contains("adm_ingresos") ? "S" : " ");
        request.setUsoctaPagoServs(usosCuenta.contains("pago_servicios") ? "S" : " ");
        request.setUsoctaDeptoNomin(usosCuenta.contains("deposito_nomina") ? "S" : " ");
        request.setUsoctaOtros(usosCuenta.contains("otros") ? "S" : " ");
        request.setOperaDepositos(resolveFlagValue(httpRequest, "operaDepositos"));
        request.setOperaRetiros(resolveFlagValue(httpRequest, "operaRetiros"));
        request.setOperaCobroGiros(resolveFlagValue(httpRequest, "operaCobroGiros"));
        request.setOperaTransferencias(resolveFlagValue(httpRequest, "operaTransferencias"));
        if (request.getDomRegOcupacion() == null || request.getDomRegOcupacion().isBlank()) {
            request.setDomRegOcupacion(DEFAULT_DOM_REG_OCUPACION);
        }
        normalizeSelectFieldsToBlank(request);
        populateDocumentCodes(request, httpRequest);

        LOGGER.info("Solicitud recibida desde onboarding para alta de cliente");

        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .findFirst()
                    .map(error -> defaultMessage(error.getDefaultMessage()))
                    .orElse("Solicitud inválida.");
            LOGGER.warn("Solicitud inválida desde onboarding: {}", message);
            redirectAttributes.addFlashAttribute("altaClienteError", message);
            return "redirect:" + ONBOARDING_BASE_PATH + "/" + CONFIRMATION_STEP;
        }

        try {
            AltaClienteResponseDto response = altaClienteService.altaCliente(request);
            LOGGER.info("Alta de cliente completada correctamente");
            redirectAttributes.addFlashAttribute(
                    "altaClienteSuccess",
                    "Envío exitoso. Perfil: " + response.getPerfil() + " | Id. Interna: " + response.getIdInterna()
            );
        } catch (BadRequestException exception) {
            LOGGER.warn("Validación de negocio rechazada durante altaCliente: {}", exception.getMessage());
            redirectAttributes.addFlashAttribute("altaClienteError", exception.getMessage());
        } catch (ServiceIntegrationException exception) {
            LOGGER.error("Error al enviar altaCliente: {}", exception.getMessage(), exception);
            redirectAttributes.addFlashAttribute("altaClienteError", GENERIC_INTEGRATION_ERROR);
        }

        return "redirect:" + ONBOARDING_BASE_PATH + "/" + CONFIRMATION_STEP;
    }

    private void addOnboardingModelAttributes(int step, Model model) {
        model.addAttribute("step", step);
        model.addAttribute("totalSteps", TOTAL_STEPS);
        model.addAttribute("progress", Math.round((step * 100.0f) / TOTAL_STEPS));
        model.addAttribute("stepLabels", STEP_LABELS);
        model.addAttribute("catalogoPaises", CountryCatalog.countries());
        model.addAttribute("catalogoRegimenFiscal", FiscalRegimeCatalog.fiscalRegimes());
        model.addAttribute("hasPrevious", step > FIRST_STEP);
        model.addAttribute("hasNext", step < TOTAL_STEPS);
        model.addAttribute("previousStep", Math.max(step - 1, FIRST_STEP));
        model.addAttribute("nextStep", Math.min(step + 1, TOTAL_STEPS));
    }

    private void addCurpModelAttributes(String curp, Model model) {
        if (curp == null || curp.trim().isEmpty()) {
            setCurpDataModel(model, new CurpData("", "", "", "", ""));
            return;
        }

        try {
            setCurpDataModel(model, curpUtils.extractCurpData(curp));
        } catch (BadRequestException exception) {
            model.addAttribute("curpError", exception.getMessage());
            setCurpDataModel(model, new CurpData("", "", "", "", ""));
        }
    }

    private boolean isStepOutOfRange(int step) {
        return step < FIRST_STEP || step > TOTAL_STEPS;
    }

    private AltaClienteRequestDto buildClienteForOnboarding() {
        AltaClienteRequestDto request = new AltaClienteRequestDto();
        request.setDomRegOcupacion(DEFAULT_DOM_REG_OCUPACION);
        return request;
    }

    private Set<String> resolveMultiValues(HttpServletRequest request, String fieldName) {
        Set<String> resolvedValues = new HashSet<>();
        String[] requestValues = request.getParameterValues(fieldName);
        if (requestValues == null || requestValues.length == 0) {
            return resolvedValues;
        }
        for (String rawValue : requestValues) {
            if (rawValue == null || rawValue.isBlank()) {
                continue;
            }
            for (String splitValue : rawValue.split(",")) {
                if (!splitValue.isBlank()) {
                    resolvedValues.add(splitValue.trim());
                }
            }
        }
        return resolvedValues;
    }

    private String resolveFlagValue(HttpServletRequest request, String fieldName) {
        String[] values = request.getParameterValues(fieldName);
        if (values == null || values.length == 0) {
            return BLANK_VALUE;
        }
        for (String value : values) {
            if ("S".equalsIgnoreCase(value)) {
                return "S";
            }
        }
        return BLANK_VALUE;
    }

    private void populateDocumentCodes(AltaClienteRequestDto request, HttpServletRequest httpRequest) {
        if (!hasDocumentCheckboxSelection(httpRequest)) {
            request.setCodDoc1(valueOrBlank(request.getCodDoc1()));
            request.setDescCod1(valueOrBlank(request.getDescCod1()));
            request.setCodDoc2(valueOrBlank(request.getCodDoc2()));
            request.setDescCod2(valueOrBlank(request.getDescCod2()));
            request.setCodDoc3(valueOrBlank(request.getCodDoc3()));
            request.setDescCod3(valueOrBlank(request.getDescCod3()));
            request.setCodDoc4(valueOrBlank(request.getCodDoc4()));
            request.setDescCod4(valueOrBlank(request.getDescCod4()));
            return;
        }
        setDocumentCodePair(
                request,
                1,
                isChecked(httpRequest, "docComprobanteIngresos"),
                "23",
                "COMPROBANTE DE INGRESOS"
        );
        setDocumentCodePair(
                request,
                2,
                isChecked(httpRequest, "docActaConstitutiva"),
                "2A",
                "ACTA CONSTITUTIVA"
        );
        setDocumentCodePair(
                request,
                3,
                isChecked(httpRequest, "docActaDefuncion"),
                "VL",
                "ACTA DE DEFUNCION"
        );
        setDocumentCodePair(
                request,
                4,
                isChecked(httpRequest, "docActaNacimiento"),
                "VK",
                "ACTA DE NACIMIENTO"
        );
    }

    private boolean hasDocumentCheckboxSelection(HttpServletRequest request) {
        String[] documentFields = {
                "docComprobanteIngresos",
                "docActaConstitutiva",
                "docActaDefuncion",
                "docActaNacimiento"
        };
        for (String documentField : documentFields) {
            if (request.getParameter(documentField) != null) {
                return true;
            }
        }
        return false;
    }

    private void normalizeSelectFieldsToBlank(AltaClienteRequestDto request) {
        request.setCodPaisNal(valueOrBlank(request.getCodPaisNal()));
        request.setCodPaisRes(valueOrBlank(request.getCodPaisRes()));
        request.setRegFisc(valueOrBlank(request.getRegFisc()));
        request.setCodEdoCivil(valueOrBlank(request.getCodEdoCivil()));
        request.setCodRegMatrimonio(valueOrBlank(request.getCodRegMatrimonio()));
        request.setCodCno(valueOrBlank(request.getCodCno()));
        request.setCodCnae(valueOrBlank(request.getCodCnae()));
        request.setDomTipoCalle(valueOrBlank(request.getDomTipoCalle()));
    }

    private String valueOrBlank(String value) {
        return value == null || value.isBlank() ? BLANK_VALUE : value;
    }

    private void setCurpDataModel(Model model, CurpData curpData) {
        model.addAttribute("curp", curpData.curp());
        model.addAttribute("generoCurp", curpData.genero());
        model.addAttribute("fechaNacimientoCurp", curpData.fechaNacimiento());
        model.addAttribute("entidadFederativaCurp", curpData.entidadFederativa());
        model.addAttribute("paisCurp", curpData.pais());
    }

    private static String defaultMessage(String message) {
        return message == null ? "Solicitud inválida." : message;
    }

    private boolean isChecked(HttpServletRequest request, String fieldName) {
        return request.getParameter(fieldName) != null;
    }

    private void setDocumentCodePair(AltaClienteRequestDto request, int index, boolean selected, String code, String description) {
        String resolvedCode = selected ? code : BLANK_VALUE;
        String resolvedDescription = selected ? description : BLANK_VALUE;
        switch (index) {
            case 1 -> {
                request.setCodDoc1(resolvedCode);
                request.setDescCod1(resolvedDescription);
            }
            case 2 -> {
                request.setCodDoc2(resolvedCode);
                request.setDescCod2(resolvedDescription);
            }
            case 3 -> {
                request.setCodDoc3(resolvedCode);
                request.setDescCod3(resolvedDescription);
            }
            case 4 -> {
                request.setCodDoc4(resolvedCode);
                request.setDescCod4(resolvedDescription);
            }
            default -> throw new IllegalArgumentException("Índice de documento no soportado: " + index);
        }
    }
}
