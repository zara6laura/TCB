package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;
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
public class AltaClienteHttpRepository implements AltaClienteRepository {
    private static final String ALTA_CLIENTE_PATH = "/tcb/altacliente";
    private static final String NETWORK_ERROR_PREFIX = "Error de conectividad: ";
    private static final Logger LOGGER = LoggerFactory.getLogger(AltaClienteHttpRepository.class);
    private final RestClient restClient;

    public AltaClienteHttpRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public AltaClienteResponseDto altaCliente(AltaClienteRequestDto request) {
        LOGGER.info("Solicitud enviada a altaCliente: {}", request);
        Root root = postForRoot(request, ALTA_CLIENTE_PATH);
        String perfil = firstNonBlank(
                read(root, "perfil"),
                read(root, "PERFIL"),
                read(root, "profile")
        );
        String idInterna = firstNonBlank(
                read(root, "idInterna"),
                read(root, "ID_INTERNA"),
                read(root, "ID.INTERNA"),
                read(root, "id.interna")
        );

        if (perfil == null || idInterna == null) {
            throw new ServiceIntegrationException("La respuesta del servicio no contiene perfil e idInterna.");
        }

        return new AltaClienteResponseDto(perfil, idInterna);
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

    private static String read(Root root, String field) {
        if (root == null) {
            return null;
        }
        Object value = root.get(field);
        if (value == null) {
            return null;
        }
        String text = String.valueOf(value);
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
