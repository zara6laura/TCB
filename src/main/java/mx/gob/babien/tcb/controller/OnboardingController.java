package mx.gob.babien.tcb.controller;

import jakarta.validation.Valid;
import java.util.List;
import mx.gob.babien.tcb.exception.BadRequestException;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.model.CurpData;
import mx.gob.babien.tcb.service.AltaClienteService;
import mx.gob.babien.tcb.util.CurpUtils;
import mx.gob.babien.tcb.dto.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OnboardingController {
    private static final String ONBOARDING_BASE_PATH = "/onboarding";
    private static final int TOTAL_STEPS = 5;
    private static final int FIRST_STEP = 1;
    private static final int DOCUMENTS_STEP = 3;
    private static final int CONFIRMATION_STEP = 4;
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

    public OnboardingController(CurpUtils curpUtils, AltaClienteService altaClienteService) {
        this.curpUtils = curpUtils;
        this.altaClienteService = altaClienteService;
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
        model.addAttribute("cliente", new AltaClienteRequestDto());
        return VIEW_ONBOARDING;
    }

    @GetMapping(ONBOARDING_BASE_PATH + "/documentos")
    public String documentos() {
        return REDIRECT_DOCUMENTS_STEP;
    }

    @PostMapping(ONBOARDING_BASE_PATH + "/altacliente")
    public String altaClienteFormSubmit(
            @Valid @ModelAttribute AltaClienteRequestDto request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .findFirst()
                    .map(error -> error.getDefaultMessage() == null ? "Solicitud inválida." : error.getDefaultMessage())
                    .orElse("Solicitud inválida.");
            redirectAttributes.addFlashAttribute("altaClienteError", message);
            return "redirect:" + ONBOARDING_BASE_PATH + "/" + CONFIRMATION_STEP;
        }

        try {
            AltaClienteResponseDto response = altaClienteService.altaCliente(request);
            redirectAttributes.addFlashAttribute(
                    "altaClienteSuccess",
                    "Envío exitoso. Perfil: " + response.getPerfil() + " | Id. Interna: " + response.getIdInterna()
            );
        } catch (BadRequestException | ServiceIntegrationException exception) {
            redirectAttributes.addFlashAttribute("altaClienteError", exception.getMessage());
        }

        return "redirect:" + ONBOARDING_BASE_PATH + "/" + CONFIRMATION_STEP;
    }

    private void addOnboardingModelAttributes(int step, Model model) {
        model.addAttribute("step", step);
        model.addAttribute("totalSteps", TOTAL_STEPS);
        model.addAttribute("progress", Math.round((step * 100.0f) / TOTAL_STEPS));
        model.addAttribute("stepLabels", STEP_LABELS);
        model.addAttribute("hasPrevious", step > FIRST_STEP);
        model.addAttribute("hasNext", step < TOTAL_STEPS);
        model.addAttribute("previousStep", Math.max(step - 1, FIRST_STEP));
        model.addAttribute("nextStep", Math.min(step + 1, TOTAL_STEPS));
    }

    private void addCurpModelAttributes(String curp, Model model) {
        CurpData curpData = new CurpData("", "", "", "", "");

        if (curp != null && !curp.trim().isEmpty()) {
            try {
                curpData = curpUtils.extractCurpData(curp);
            } catch (BadRequestException exception) {
                model.addAttribute("curpError", exception.getMessage());
            }
        }

        model.addAttribute("curp", curpData.curp());
        model.addAttribute("generoCurp", curpData.genero());
        model.addAttribute("fechaNacimientoCurp", curpData.fechaNacimiento());
        model.addAttribute("entidadFederativaCurp", curpData.entidadFederativa());
        model.addAttribute("paisCurp", curpData.pais());
    }

    private boolean isStepOutOfRange(int step) {
        return step < FIRST_STEP || step > TOTAL_STEPS;
    }
}
