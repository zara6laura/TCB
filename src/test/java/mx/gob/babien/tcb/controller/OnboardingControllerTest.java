package mx.gob.babien.tcb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;
import mx.gob.babien.tcb.dto.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.dto.ClienteEncontradoDto;
import mx.gob.babien.tcb.service.AltaClienteService;
import mx.gob.babien.tcb.service.ClienteBusquedaService;
import mx.gob.babien.tcb.util.CurpUtils;
import java.util.List;

@WebMvcTest({
        OnboardingController.class,
        AperturaCuentaController.class
})
@Import(CurpUtils.class)
class OnboardingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AltaClienteService altaClienteService;

    @MockBean
    private ClienteBusquedaService clienteBusquedaService;

    @Test
    void shouldRedirectRootToFirstOnboardingStep() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/onboarding/1"));
    }

    @Test
    void shouldRenderOnboardingForValidStep() throws Exception {
        mockMvc.perform(get("/onboarding/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("onboarding"))
                .andExpect(model().attribute("step", 2))
                .andExpect(model().attribute("totalSteps", 5))
                .andExpect(model().attribute("progress", 40))
                .andExpect(model().attribute("hasPrevious", true))
                .andExpect(model().attribute("hasNext", true))
                .andExpect(model().attribute("previousStep", 1))
                .andExpect(model().attribute("nextStep", 3))
                .andExpect(model().attribute("curp", ""))
                .andExpect(model().attribute("generoCurp", ""))
                .andExpect(model().attribute("fechaNacimientoCurp", ""))
                .andExpect(model().attribute("entidadFederativaCurp", ""))
                .andExpect(model().attribute("paisCurp", ""));
    }

    @Test
    void shouldRedirectToFirstStepForOutOfRangeStep() throws Exception {
        mockMvc.perform(get("/onboarding/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/onboarding/1"));
    }

    @Test
    void shouldRedirectDocumentosToStepThree() throws Exception {
        mockMvc.perform(get("/onboarding/documentos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/onboarding/3"));
    }

    @Test
    void shouldUseDefaultAccountTypeWhenMissing() throws Exception {
        mockMvc.perform(get("/onboarding/apertura-cuenta/detalle"))
                .andExpect(status().isOk())
                .andExpect(view().name("apertura-cuenta-detalle"))
                .andExpect(model().attribute("tipoCuenta", "CUENTA AHORRO TRADICIONAL"));
    }

    @Test
    void shouldReturnCurpDataWhenCurpIsValid() throws Exception {
        mockMvc.perform(get("/onboarding/curp-datos").param("curp", "PASG800809MDFZNA05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genero").value("M"))
                .andExpect(jsonPath("$.fechaNacimiento").value("1980/08/09"))
                .andExpect(jsonPath("$.entidadFederativa").value("DISTRITO FEDERAL"))
                .andExpect(jsonPath("$.pais").value("MEXICO"));
    }

    @Test
    void shouldReturnBadRequestWhenCurpIsInvalid() throws Exception {
        mockMvc.perform(get("/onboarding/curp-datos").param("curp", "CURP_INVALIDA"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturnAltaClienteResponse() throws Exception {
        given(altaClienteService.altaCliente(any())).willReturn(new AltaClienteResponseDto("BAJO", "4232566"));

        mockMvc.perform(post("/onboarding/altacliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "ID USUARIO":"USR01",
                                  "TPO-ID":"INE",
                                  "NUM-ID":"1710050789154",
                                  "NUM-SUC":"162",
                                  "NOMBRE":"GABRIEL",
                                  "PRIM-APE":"PAZ",
                                  "CURP":"PASG800809HDFZNB05",
                                  "FEC-NACTO":"1980/08/09"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.perfil").value("BAJO"))
                .andExpect(jsonPath("$.idInterna").value("4232566"));
    }

    @Test
    void shouldReturnClienteCoincidencesWhenSearching() throws Exception {
        ClienteEncontradoDto cliente = new ClienteEncontradoDto();
        cliente.setIdCte("4232566");
        cliente.setNombre("GABRIEL");
        cliente.setApePat("PAZ");
        cliente.setApeMat("SANTA ANA");
        cliente.setCurp("PASG800809HDFZNB05");
        cliente.setGenero("H");
        ClienteBusquedaResponseDto response = new ClienteBusquedaResponseDto();
        response.setEstatus(0);
        response.setCodigo("OK");
        response.setMensaje("Coincidencias encontradas");
        response.setClientes(List.of(cliente));
        given(clienteBusquedaService.buscarCliente(any())).willReturn(response);

        mockMvc.perform(post("/onboarding/buscar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "CURP":"PASG800809HDFZNB05",
                                  "NOMBRE":"GABRIEL",
                                  "APAT":"PAZ",
                                  "AMAT":"SANTA ANA",
                                  "GENERO":"H"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("OK"))
                .andExpect(jsonPath("$.clientes[0].idCte").value("4232566"))
                .andExpect(jsonPath("$.clientes[0].curp").value("PASG800809HDFZNB05"));
    }
}
