package mx.gob.babien.tcb.persistence.repository;

import java.util.List;
import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;
import mx.gob.babien.tcb.dto.response.DatosDto;
import mx.gob.babien.tcb.dto.response.RegistroDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.AltaClienteRepository;
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
public class AltaClienteHttpRepository implements AltaClienteRepository {
    private static final String NETWORK_ERROR_PREFIX = "Error de conectividad: ";
    private static final Logger LOGGER = LoggerFactory.getLogger(AltaClienteHttpRepository.class);
    private final RestClient restClient;
    private final String urlAltaCliente;

    public AltaClienteHttpRepository(
            RestClient restClient,
            @Value("${urlServicios.AltaCliente.urlAltaCliente}") String urlAltaCliente
    ) {
        this.restClient = restClient;
        this.urlAltaCliente = urlAltaCliente;
    }

    @Override
    public AltaClienteResponseDto altaCliente(AltaClienteRequestDto request) {
        LOGGER.info("Solicitud enviada a altaCliente: {}", request);
        Root root = postForRoot(request, urlAltaCliente);
        return resolveAltaClienteResponse(root);
    }

    private Root postForRoot(AltaClienteRequestDto request, String url) {
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

    private static AltaClienteResponseDto resolveAltaClienteResponse(Root root) {
        if (root == null || root.getRespuesta() == null || root.getRespuesta().getDatos() == null) {
            throw new ServiceIntegrationException("La respuesta del servicio no contiene perfil e idInterna.");
        }
        DatosDto datos = root.getRespuesta().getDatos();
        List<RegistroDto> registros = datos.getRegistros();
        if (registros == null || registros.isEmpty()) {
            throw new ServiceIntegrationException("La respuesta del servicio no contiene perfil e idInterna.");
        }
        for (RegistroDto registro : registros) {
            if (registro == null) {
                continue;
            }
            String perfil = normalize(registro.getPerfil());
            String idInterna = normalize(registro.getIdInterna());
            if (perfil != null && idInterna != null) {
                return new AltaClienteResponseDto(perfil, idInterna);
            }
        }
        throw new ServiceIntegrationException("La respuesta del servicio no contiene perfil e idInterna.");
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
