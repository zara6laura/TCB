package mx.gob.babien.tcb.persistence.repository;

import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.CatalogoGeneralRepository;
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
public class CatalogoGeneralHttpRepository implements CatalogoGeneralRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoGeneralHttpRepository.class);
    private static final String NETWORK_ERROR_PREFIX = "Error de conectividad: ";
    private final RestClient restClient;
    private final String urlCatalogos;

    public CatalogoGeneralHttpRepository(
            RestClient restClient,
            @Value("${urlServicios.AltaCliente.urlCatalogos}")
            String urlCatalogos
    ) {
        this.restClient = restClient;
        this.urlCatalogos = urlCatalogos;
    }

    @Override
    public Root consumirCatalogo(CatalogoGeneralRequestDto request) {
        CatalogoGeneralRequestDto requestDto = new CatalogoGeneralRequestDto();
        requestDto.setCodTblRef(emptyIfNull(request.getCodTblRef()));
        requestDto.setCodAplccnSubapl(emptyIfNull(request.getCodAplccnSubapl()));
        requestDto.setNumeroPagina(emptyIfNull(request.getNumeroPagina()));
        return postForRoot(requestDto, urlCatalogos);
    }

    private Root postForRoot(CatalogoGeneralRequestDto requestDto, String url) {
        try {
            Root response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestDto)
                    .retrieve()
                    .body(Root.class);

            if (response == null || response.getRespuesta() == null) {
                LOGGER.error("Respuesta nula o sin estructura en [{}]", url);
                throw new ServiceIntegrationException("Respuesta inválida del servicio externo.");
            }
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            String body = exception.getResponseBodyAsString();
            LOGGER.warn(
                    "Error HTTP {} en [{}]. Body: {}",
                    exception.getStatusCode().value(),
                    url,
                    body
            );

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
