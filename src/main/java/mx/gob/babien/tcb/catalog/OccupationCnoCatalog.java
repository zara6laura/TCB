package mx.gob.babien.tcb.catalog;

import java.util.List;

public final class OccupationCnoCatalog {

    // CNO occupation catalog: UI displays description and submits code only.
    private static final List<OccupationCnoOption> OCCUPATIONS = List.of(
            new OccupationCnoOption("01", "COMERCIO"),
            new OccupationCnoOption("02", "CONDUCTORES"),
            new OccupationCnoOption("19", "DESEMPLEADO"),
            new OccupationCnoOption("16", "ECLESIASTICO"),
            new OccupationCnoOption("14", "ESTUDIANTE"),
            new OccupationCnoOption("13", "FUNCIONARIO DE INSTITUCIONES O EMPRESAS PRIVADAS"),
            new OccupationCnoOption("12", "FUNCIONARIO DE INSTITUCIONES O EMPRESAS PUBLICAS"),
            new OccupationCnoOption("17", "HOGAR"),
            new OccupationCnoOption("18", "JUBILADOS O PENSIONADOS"),
            new OccupationCnoOption("15", "MENOR DE EDAD"),
            new OccupationCnoOption("10", "PROFESIONISTA ASALARIADO"),
            new OccupationCnoOption("11", "PROFESIONISTA INDEPENDIENTE"),
            new OccupationCnoOption("09", "TECNICOS"),
            new OccupationCnoOption("03", "TRABAJADORES ADMINISTRATIVOS"),
            new OccupationCnoOption("06", "TRABAJADORES DE ACTIV. DE AGRICULTURA / GANADERIA"),
            new OccupationCnoOption("04", "TRABAJADORES DE LA EDUCACION"),
            new OccupationCnoOption("05", "TRABAJADORES DEL ARTE"),
            new OccupationCnoOption("07", "TRABAJADORES EN SERVICIOS DE PROTECCION"),
            new OccupationCnoOption("08", "TRABAJADORES FABRILES")
    );

    private OccupationCnoCatalog() {
    }

    public static List<OccupationCnoOption> occupations() {
        return OCCUPATIONS;
    }

    public record OccupationCnoOption(String code, String description) {
    }
}
