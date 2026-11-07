package mx.gob.babien.tcb.catalog;

import java.util.List;

public final class StreetTypeCatalog {

    // Street type catalog: UI shows description, form submits code only.
    private static final List<StreetTypeOption> STREET_TYPES = List.of(
            new StreetTypeOption("AL", "ALAMEDA"),
            new StreetTypeOption("AV", "AVENIDA"),
            new StreetTypeOption("BL", "BOULEVARD"),
            new StreetTypeOption("CL", "CALLE"),
            new StreetTypeOption("CM", "CAMINO"),
            new StreetTypeOption("CR", "CTRA"),
            new StreetTypeOption("EV", "EJE VIAL"),
            new StreetTypeOption("EN", "ENTRADA"),
            new StreetTypeOption("EX", "EXPLANADA"),
            new StreetTypeOption("EJ", "EXTRANJERO"),
            new StreetTypeOption("GL", "GLORIETA"),
            new StreetTypeOption("KM", "KILOMETRO"),
            new StreetTypeOption("PA", "PARAJE"),
            new StreetTypeOption("PE", "PARQUE"),
            new StreetTypeOption("PJ", "PASAJE"),
            new StreetTypeOption("PS", "PASEO"),
            new StreetTypeOption("PZ", "PLAZA"),
            new StreetTypeOption("PR", "PRLG"),
            new StreetTypeOption("PT", "PUENTE"),
            new StreetTypeOption("PU", "PUERTA"),
            new StreetTypeOption("RS", "RCIAL"),
            new StreetTypeOption("SA", "SALIDA"),
            new StreetTypeOption("SD", "SENDERO"),
            new StreetTypeOption("SB", "SUBIDA"),
            new StreetTypeOption("VI", "VIA"),
            new StreetTypeOption("VP", "VIA PUBLICA")
    );

    private StreetTypeCatalog() {
    }

    public static List<StreetTypeOption> streetTypes() {
        return STREET_TYPES;
    }

    public record StreetTypeOption(String code, String description) {
    }
}
