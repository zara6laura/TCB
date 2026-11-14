package mx.gob.babien.tcb.persistence.repository;

import mx.gob.babien.tcb.dto.request.CodigoPostalRequestDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.CodigoPostalRepository;
import mx.gob.babien.tcb.util.ServiceErrorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Repository
public class CodigoPostalHttpRepository implements CodigoPostalRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodigoPostalHttpRepository.class);
    private static final String NETWORK_ERROR_PREFIX = "Error de conectividad: ";

    private final RestClient restClient;
    private final String urlCodigosPostal;

    public CodigoPostalHttpRepository(
            RestClient restClient,
            @Value("${urlServicios.AltaCliente.urlCodigosPostal}") String urlCodigosPostal
    ) {
        this.restClient = restClient;
        this.urlCodigosPostal = urlCodigosPostal;
    }

    @Override
    public Root consumirCodigoPostal(CodigoPostalRequestDto request) {
        CodigoPostalRequestDto requestDto = new CodigoPostalRequestDto();
        requestDto.setCodPostal(emptyIfNull(request.getCodPostal()));
        return postForRoot(requestDto, urlCodigosPostal);
    }

    private Root postForRoot(CodigoPostalRequestDto requestDto, String url) {
        try {
            Root root = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestDto)
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

    private static String emptyIfNull(String value) {
        return value == null ? "" : value;
    }
}
