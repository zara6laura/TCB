package mx.gob.babien.tcb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.gob.babien.tcb.dto.response.ErrorDto;
import mx.gob.babien.tcb.dto.response.Root;

public final class ServiceErrorParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ServiceErrorParser() {
    }

    public static Root tryParseRoot(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(body, Root.class);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public static String buildErrorMessage(String body, int statusCode) {
        Root parsed = tryParseRoot(body);
        if (parsed == null || parsed.getRespuesta() == null || parsed.getRespuesta().getError() == null) {
            return "Error HTTP " + statusCode + " al consumir el servicio de catálogos.";
        }

        ErrorDto error = parsed.getRespuesta().getError();
        String codigo = normalize(error.getCodigoError());
        String descripcion = normalize(error.getDescError());

        if (descripcion == null && codigo == null) {
            return "Error HTTP " + statusCode + " al consumir el servicio de catálogos.";
        }
        if (descripcion == null) {
            return "Error de servicio externo [" + codigo + "].";
        }
        if (codigo == null) {
            return descripcion;
        }
        return "Error de servicio externo [" + codigo + "]: " + descripcion;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
