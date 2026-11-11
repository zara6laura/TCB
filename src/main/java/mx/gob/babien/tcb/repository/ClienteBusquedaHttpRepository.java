package mx.gob.babien.tcb.repository;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class ClienteBusquedaHttpRepository implements ClienteBusquedaRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteBusquedaHttpRepository.class);
    private static final String BUCTENOM_PATH = "/Clientes/BUCTENOM";
    private final RestClient restClient;

    public ClienteBusquedaHttpRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public JsonNode buscarCliente(Map<String, String> payload) {
        try {
            LOGGER.info("Payload enviado a BUCTENOM: {}", payload);
            JsonNode response = restClient.post()
                    .uri(BUCTENOM_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(JsonNode.class);

            if (response == null) {
                throw new ServiceIntegrationException("El servicio BUCTENOM respondió vacío.");
            }
            LOGGER.info("Respuesta recibida de BUCTENOM: {}", response);
            return response;
        } catch (ServiceIntegrationException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new ServiceIntegrationException("No fue posible consumir el servicio BUCTENOM.", exception);
        }
    }
}
