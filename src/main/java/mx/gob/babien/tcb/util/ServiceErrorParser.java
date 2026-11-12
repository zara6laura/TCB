package mx.gob.babien.tcb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralRawResponseDto;
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

    public static CatalogoGeneralRawResponseDto tryParseCatalogRoot(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(body, CatalogoGeneralRawResponseDto.class);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public static JsonNode tryParseJsonNode(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(body);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public static String buildErrorMessage(String body, int statusCode) {
        JsonNode parsedJson = tryParseJsonNode(body);
        if (parsedJson != null) {
            String fromJson = resolveJsonErrorMessage(parsedJson);
            if (fromJson != null) {
                return fromJson;
            }
        }

        CatalogoGeneralRawResponseDto parsed = tryParseCatalogRoot(body);
        if (parsed == null || parsed.getRespuesta() == null || parsed.getRespuesta().getError() == null) {
            return "Error HTTP " + statusCode + " al consumir el servicio de catálogos.";
        }

        CatalogoGeneralRawResponseDto.ErrorDto error = parsed.getRespuesta().getError();
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

    private static String resolveJsonErrorMessage(JsonNode node) {
        String directMessage = firstNonBlank(
                textAt(node, "message"),
                textAt(node, "mensaje"),
                textAt(node, "error"),
                textAt(node, "error_description"),
                textAt(node, "detail")
        );
        if (directMessage != null) {
            return directMessage;
        }

        JsonNode respuesta = node.get("respuesta");
        if (respuesta == null || respuesta.isNull()) {
            return null;
        }
        JsonNode error = respuesta.get("error");
        if (error == null || error.isNull()) {
            return null;
        }

        String codigo = normalize(textAt(error, "codigoError"));
        String descripcion = normalize(textAt(error, "descError"));
        if (descripcion == null && codigo == null) {
            return null;
        }
        if (descripcion == null) {
            return "Error de servicio externo [" + codigo + "].";
        }
        if (codigo == null) {
            return descripcion;
        }
        return "Error de servicio externo [" + codigo + "]: " + descripcion;
    }

    private static String textAt(JsonNode node, String fieldName) {
        if (node == null || node.isNull()) {
            return null;
        }
        JsonNode value = node.get(fieldName);
        if (value == null || value.isNull()) {
            return null;
        }
        String text = value.asText();
        return normalize(text);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
