package mx.gob.babien.tcb.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import mx.gob.babien.tcb.exception.BadRequestException;
import mx.gob.babien.tcb.model.CurpData;
import org.springframework.stereotype.Component;

@Component
public class CurpUtils {
    private static final Pattern CURP_PATTERN = Pattern.compile("^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]\\d$");
    private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final Map<String, String> CURP_STATE_MAP = Map.ofEntries(
            Map.entry("AS", "AGUASCALIENTES"),
            Map.entry("BC", "BAJA CALIFORNIA"),
            Map.entry("BS", "BAJA CALIFORNIA SUR"),
            Map.entry("CC", "CAMPECHE"),
            Map.entry("CL", "COAHUILA"),
            Map.entry("CM", "COLIMA"),
            Map.entry("CS", "CHIAPAS"),
            Map.entry("CH", "CHIHUAHUA"),
            Map.entry("DF", "DISTRITO FEDERAL"),
            Map.entry("DG", "DURANGO"),
            Map.entry("GT", "GUANAJUATO"),
            Map.entry("GR", "GUERRERO"),
            Map.entry("HG", "HIDALGO"),
            Map.entry("JC", "JALISCO"),
            Map.entry("MC", "MEXICO"),
            Map.entry("MN", "MICHOACAN"),
            Map.entry("MS", "MORELOS"),
            Map.entry("NT", "NAYARIT"),
            Map.entry("NL", "NUEVO LEON"),
            Map.entry("OC", "OAXACA"),
            Map.entry("PL", "PUEBLA"),
            Map.entry("QT", "QUERETARO"),
            Map.entry("QR", "QUINTANA ROO"),
            Map.entry("SP", "SAN LUIS POTOSI"),
            Map.entry("SL", "SINALOA"),
            Map.entry("SR", "SONORA"),
            Map.entry("TC", "TABASCO"),
            Map.entry("TS", "TAMAULIPAS"),
            Map.entry("TL", "TLAXCALA"),
            Map.entry("VZ", "VERACRUZ"),
            Map.entry("YN", "YUCATAN"),
            Map.entry("ZS", "ZACATECAS"),
            Map.entry("NE", "NACIDO EN EL EXTRANJERO")
    );

    public CurpData extractCurpData(String curp) {
        String normalizedCurp = curp == null ? "" : curp.trim().toUpperCase(Locale.ROOT);
        if (!CURP_PATTERN.matcher(normalizedCurp).matches()) {
            throw new BadRequestException("La CURP no tiene un formato válido.");
        }

        String rawDate = normalizedCurp.substring(4, 10);
        String entityCode = normalizedCurp.substring(11, 13);
        int year = Integer.parseInt(rawDate.substring(0, 2));
        int month = Integer.parseInt(rawDate.substring(2, 4));
        int day = Integer.parseInt(rawDate.substring(4, 6));

        int fullYear = Character.isDigit(normalizedCurp.charAt(16)) ? 1900 + year : 2000 + year;
        LocalDate birthDate;
        try {
            birthDate = LocalDate.of(fullYear, month, day);
        } catch (DateTimeException exception) {
            throw new BadRequestException("La CURP contiene una fecha de nacimiento inválida.");
        }

        String entidadFederativa = CURP_STATE_MAP.get(entityCode);
        if (entidadFederativa == null) {
            throw new BadRequestException("La CURP contiene una entidad federativa inválida.");
        }

        return new CurpData(
                normalizedCurp,
                String.valueOf(normalizedCurp.charAt(10)),
                birthDate.format(BIRTH_DATE_FORMATTER),
                entidadFederativa,
                "NE".equals(entityCode) ? "EXTRANJERO" : "MEXICO"
        );
    }
}
