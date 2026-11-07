package mx.gob.babien.tcb.catalog;

import java.util.List;

public final class FiscalRegimeCatalog {

    // SAT fiscal regimes: UI shows description, form submits code only.
    private static final List<FiscalRegimeOption> FISCAL_REGIMES = List.of(
            new FiscalRegimeOption("22", "ACTIV AGRIC GANAD SILVIC PESQU"),
            new FiscalRegimeOption("06", "ARRENDAMIENTO"),
            new FiscalRegimeOption("24", "COORDINADOS"),
            new FiscalRegimeOption("08", "DEMAS INGRESOS"),
            new FiscalRegimeOption("01", "GENERAL LEY PERSONAS MORALES"),
            new FiscalRegimeOption("21", "INCORPORACION FISCAL"),
            new FiscalRegimeOption("11", "INGR POR DIV SOCIOS Y ACCIONIS"),
            new FiscalRegimeOption("14", "INGRESOS POR INTERESES"),
            new FiscalRegimeOption("23", "OPCIONAL PARA GRUPO SOCIEDADES"),
            new FiscalRegimeOption("12", "PERS FISICAS CON ACT EMPR PROF"),
            new FiscalRegimeOption("03", "PERS MORALES CON FINES NO LUCR"),
            new FiscalRegimeOption("25", "REG ACT EMPR CON INGR TECNOLOG"),
            new FiscalRegimeOption("15", "REGIM INGRESOS POR OBT PREMIOS"),
            new FiscalRegimeOption("07", "REGIMEN DE ENAJEN O ADQ BIENES"),
            new FiscalRegimeOption("26", "REGIMEN SIMPLIFIC DE CONFIANZA"),
            new FiscalRegimeOption("10", "RES EN EXTRANJ SIN PERM MEXICO"),
            new FiscalRegimeOption("16", "SIN OBLIGACIONES FISCALES"),
            new FiscalRegimeOption("20", "SOC COOP PRODUCCI DIFERIR INGR"),
            new FiscalRegimeOption("05", "SUELDOS Y SALARIOS INGR ASIMIL")
    );

    private FiscalRegimeCatalog() {
    }

    public static List<FiscalRegimeOption> fiscalRegimes() {
        return FISCAL_REGIMES;
    }

    public record FiscalRegimeOption(String code, String description) {
    }
}
