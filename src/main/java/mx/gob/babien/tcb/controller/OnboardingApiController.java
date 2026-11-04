package mx.gob.babien.tcb.controller;

import jakarta.validation.Valid;
import mx.gob.babien.tcb.dto.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;
import mx.gob.babien.tcb.dto.CurpDatosResponseDto;
import mx.gob.babien.tcb.model.CurpData;
import mx.gob.babien.tcb.service.AltaClienteService;
import mx.gob.babien.tcb.util.CurpUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnboardingApiController {
    private static final String ONBOARDING_BASE_PATH = "/onboarding";

    private final AltaClienteService altaClienteService;
    private final CurpUtils curpUtils;

    public OnboardingApiController(AltaClienteService altaClienteService, CurpUtils curpUtils) {
        this.altaClienteService = altaClienteService;
        this.curpUtils = curpUtils;
    }

    @GetMapping(ONBOARDING_BASE_PATH + "/curp-datos")
    public ResponseEntity<CurpDatosResponseDto> curpDatos(@RequestParam(name = "curp") String curp) {
        CurpData curpData = curpUtils.extractCurpData(curp);
        CurpDatosResponseDto response = new CurpDatosResponseDto(
                curpData.curp(),
                curpData.genero(),
                curpData.fechaNacimiento(),
                curpData.entidadFederativa(),
                curpData.pais()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = ONBOARDING_BASE_PATH + "/altacliente",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AltaClienteResponseDto> altaCliente(@Valid @RequestBody AltaClienteRequestDto request) {
        return ResponseEntity.ok(altaClienteService.altaCliente(request));
    }
}
