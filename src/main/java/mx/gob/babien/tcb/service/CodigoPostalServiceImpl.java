package mx.gob.babien.tcb.service;

import java.util.List;
import java.util.Objects;
import mx.gob.babien.tcb.dto.request.CodigoPostalRequestDto;
import mx.gob.babien.tcb.dto.response.CodigoPostalRegistroDto;
import mx.gob.babien.tcb.dto.response.CodigoPostalResponseDto;
import mx.gob.babien.tcb.dto.response.DatosDto;
import mx.gob.babien.tcb.dto.response.ErrorDto;
import mx.gob.babien.tcb.dto.response.RegistroDto;
import mx.gob.babien.tcb.dto.response.RespuestaDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.CodigoPostalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CodigoPostalServiceImpl implements CodigoPostalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodigoPostalServiceImpl.class);

    private final CodigoPostalRepository codigoPostalRepository;

    public CodigoPostalServiceImpl(CodigoPostalRepository codigoPostalRepository) {
        this.codigoPostalRepository = codigoPostalRepository;
    }

    @Override
    public CodigoPostalResponseDto consumirCodigoPostal(CodigoPostalRequestDto request) {
        Root root = codigoPostalRepository.consumirCodigoPostal(
                request == null ? new CodigoPostalRequestDto() : request
        );
        RespuestaDto respuesta = root == null ? null : root.getRespuesta();
        if (respuesta == null) {
            throw new ServiceIntegrationException("La respuesta del servicio de código postal no tiene el nodo 'respuesta'.");
        }

        CodigoPostalResponseDto result = new CodigoPostalResponseDto();
        result.setEstatus(respuesta.getEstatus() == null ? 0 : respuesta.getEstatus());

        ErrorDto error = respuesta.getError();
        result.setCodigoError(error == null ? null : normalize(error.getCodigoError()));
        result.setDescError(error == null ? null : normalize(error.getDescError()));

        DatosDto datos = respuesta.getDatos();
        result.setRegistros(datos == null ? List.of() : resolveRegistros(datos));
        LOGGER.info("Respuesta codigoPostal: {}", result);
        return result;
    }

    private List<CodigoPostalRegistroDto> resolveRegistros(DatosDto datos) {
        if (datos.getRegistros() == null || datos.getRegistros().isEmpty()) {
            return List.of();
        }
        return datos.getRegistros().stream()
                .filter(Objects::nonNull)
                .map(this::toRegistro)
                .filter(this::hasPostalData)
                .toList();
    }

    private CodigoPostalRegistroDto toRegistro(RegistroDto registro) {
        CodigoPostalRegistroDto dto = new CodigoPostalRegistroDto();
        dto.setCodPostal(normalize(registro.getCodigoPostal()));
        dto.setCodLocalidad(normalize(registro.getCodLocalidad()));
        dto.setNomLocalidad(normalize(registro.getNomLocalidad()));
        dto.setCodMncpio(normalize(registro.getCodMncpio()));
        dto.setNomMncpio(normalize(registro.getNomMncpio()));
        dto.setCodCiudad(normalize(registro.getCodCiudad()));
        dto.setNomCiudad(normalize(registro.getNomCiudad()));
        dto.setCodEstado(normalize(registro.getCodEstado()));
        dto.setNomEstado(normalize(registro.getNomEstado()));
        dto.setCodPais(normalize(registro.getCodPais()));
        dto.setNomPais(normalize(registro.getNomPais()));
        return dto;
    }

    private boolean hasPostalData(CodigoPostalRegistroDto registro) {
        return hasText(registro.getCodPostal())
                || hasText(registro.getNomLocalidad())
                || hasText(registro.getNomEstado());
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
