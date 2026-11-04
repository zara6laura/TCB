package mx.gob.babien.tcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelacionarPersonasCapturaController {
    private static final String ONBOARDING_BASE_PATH = "/onboarding";
    private static final String VIEW_RELACIONAR_PERSONAS_CAPTURA = "relacionar-personas-captura";

    @GetMapping(ONBOARDING_BASE_PATH + "/relacionar-personas/nuevo")
    public String relacionarPersonasNuevo() {
        return VIEW_RELACIONAR_PERSONAS_CAPTURA;
    }
}
