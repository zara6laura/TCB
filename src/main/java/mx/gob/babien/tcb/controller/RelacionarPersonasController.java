package mx.gob.babien.tcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelacionarPersonasController {
    private static final String ONBOARDING_BASE_PATH = "/onboarding";
    private static final String VIEW_RELACIONAR_PERSONAS = "relacionar-personas";

    @GetMapping(ONBOARDING_BASE_PATH + "/relacionar-personas")
    public String relacionarPersonas() {
        return VIEW_RELACIONAR_PERSONAS;
    }
}
