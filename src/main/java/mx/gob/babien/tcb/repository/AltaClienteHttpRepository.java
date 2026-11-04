package mx.gob.babien.tcb.repository;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class AltaClienteHttpRepository implements AltaClienteRepository {
    private static final String ALTA_CLIENTE_PATH = "/tcb/altacliente";
    private final RestClient restClient;

    public AltaClienteHttpRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public AltaClienteResponseDto altaCliente(Map<String, String> payload) {
        try {
            JsonNode serviceResponse = restClient.post()
                    .uri(ALTA_CLIENTE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(JsonNode.class);

            if (serviceResponse == null) {
                throw new ServiceIntegrationException("El servicio de alta cliente respondió vacío.");
            }

            String perfil = firstNonBlank(
                    read(serviceResponse, "perfil"),
                    read(serviceResponse, "PERFIL"),
                    read(serviceResponse, "profile")
            );
            String idInterna = firstNonBlank(
                    read(serviceResponse, "idInterna"),
                    read(serviceResponse, "ID_INTERNA"),
                    read(serviceResponse, "ID.INTERNA"),
                    read(serviceResponse, "id.interna")
            );

            if (perfil == null || idInterna == null) {
                throw new ServiceIntegrationException("La respuesta del servicio no contiene perfil e idInterna.");
            }

            return new AltaClienteResponseDto(perfil, idInterna);
        } catch (ServiceIntegrationException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new ServiceIntegrationException("No fue posible consumir el servicio de alta cliente.", exception);
        }
    }

    private static String read(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) {
            return null;
        }
        String text = value.asText();
        return text == null || text.isBlank() ? null : text.trim();
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
