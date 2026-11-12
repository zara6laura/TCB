package mx.gob.babien.tcb.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralRawResponseDto;
import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
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
            @Value("${urlServicios.AltaCliente.urlCatalogos:http://172.16.17.7:8290/ApiCatalogoTcbFtFila/ObtenerCatalogoTcbFtFila}")
            String urlCatalogos
    ) {
        this.restClient = restClient;
        this.urlCatalogos = urlCatalogos;
    }

    @Override
    public CatalogoGeneralRawResponseDto consumirCatalogo(CatalogoGeneralRequestDto request) {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("COD_TBL_REF", emptyIfNull(request.getCodTblRef()));
        payload.put("COD_APLCCN_SUBAPL", emptyIfNull(request.getCodAplccnSubapl()));
        payload.put("numeropagina", emptyIfNull(request.getNumeroPagina()));
        return postForRoot(payload, urlCatalogos);
    }

    private CatalogoGeneralRawResponseDto postForRoot(Object request, String url) {
        try {
            CatalogoGeneralRawResponseDto response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(CatalogoGeneralRawResponseDto.class);

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

            CatalogoGeneralRawResponseDto parsed = ServiceErrorParser.tryParseCatalogRoot(body);
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
