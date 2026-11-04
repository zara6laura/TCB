package mx.gob.babien.tcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AperturaCuentaController {
    private static final String APERTURA_CUENTA_PATH = "/onboarding/apertura-cuenta";
    private static final String REDIRECT_APERTURA_CUENTA_DETALLE = "redirect:" + APERTURA_CUENTA_PATH + "/detalle";
    private static final String VIEW_APERTURA_CUENTA_DETALLE = "apertura-cuenta-detalle";
    private static final String DEFAULT_ACCOUNT_TYPE = "CUENTA AHORRO TRADICIONAL";

    @GetMapping(APERTURA_CUENTA_PATH)
    public String aperturaCuenta() {
        return REDIRECT_APERTURA_CUENTA_DETALLE;
    }

    @GetMapping(APERTURA_CUENTA_PATH + "/detalle")
    public String aperturaCuentaDetalle(
            @RequestParam(name = "tipo", defaultValue = DEFAULT_ACCOUNT_TYPE) String tipo,
            Model model
    ) {
        model.addAttribute("tipoCuenta", tipo);
        return VIEW_APERTURA_CUENTA_DETALLE;
    }
}
