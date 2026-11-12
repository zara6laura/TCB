package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.util.ServiceErrorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Repository
public class ClienteBusquedaHttpRepository implements ClienteBusquedaRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteBusquedaHttpRepository.class);
    private static final String BUCTENOM_PATH = "/Clientes/BUCTENOM";
    private static final String NETWORK_ERROR_PREFIX = "Error de conectividad: ";
    private final RestClient restClient;

    public ClienteBusquedaHttpRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Root buscarCliente(ClienteBusquedaRequestDto request) {
        LOGGER.info("Solicitud enviada a BUCTENOM: {}", request);
        Root root = postForRoot(request, BUCTENOM_PATH);
        LOGGER.info("Respuesta recibida de BUCTENOM");
        return root;
    }

    private Root postForRoot(Object request, String url) {
        try {
            Root root = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(Root.class);

            if (root == null || root.getRespuesta() == null) {
                LOGGER.error("Respuesta nula o sin estructura en [{}]", url);
                throw new ServiceIntegrationException("Respuesta inválida del servicio externo.");
            }
            return root;
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            String body = exception.getResponseBodyAsString();
            LOGGER.warn("Error HTTP {} en [{}]. Body: {}", exception.getStatusCode().value(), url, body);

            Root parsed = ServiceErrorParser.tryParseRoot(body);
            if (parsed != null) {
                return parsed;
            }

            throw new ServiceIntegrationException(
                    ServiceErrorParser.buildErrorMessage(body, exception.getStatusCode().value()),
                    exception
            );
        } catch (ResourceAccessException exception) {
            LOGGER.error("Sin conexión al servicio [{}]: {}", url, exception.getMessage());
            throw new ServiceIntegrationException(
                    NETWORK_ERROR_PREFIX + "No se pudo conectar con el servicio externo. Verifique la red o intente más tarde.",
                    exception
            );
        } catch (ServiceIntegrationException exception) {
            throw exception;
        } catch (RestClientException exception) {
            LOGGER.error("Error inesperado al consumir [{}]: {}", url, exception.getMessage(), exception);
            throw new ServiceIntegrationException("Error inesperado al procesar la solicitud.", exception);
        }
    }
}
