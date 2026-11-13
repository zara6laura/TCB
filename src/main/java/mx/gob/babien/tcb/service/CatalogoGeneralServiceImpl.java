package mx.gob.babien.tcb.service;

import java.util.List;
import java.util.Objects;
import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralResponseDto;
import mx.gob.babien.tcb.dto.response.CatalogoRegistroDto;
import mx.gob.babien.tcb.dto.response.DatosDto;
import mx.gob.babien.tcb.dto.response.ErrorDto;
import mx.gob.babien.tcb.dto.response.RegistroDto;
import mx.gob.babien.tcb.dto.response.RespuestaDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.CatalogoGeneralRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CatalogoGeneralServiceImpl implements CatalogoGeneralService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoGeneralServiceImpl.class);
    private final CatalogoGeneralRepository catalogoGeneralRepository;

    public CatalogoGeneralServiceImpl(CatalogoGeneralRepository catalogoGeneralRepository) {
        this.catalogoGeneralRepository = catalogoGeneralRepository;
    }

    @Override
    public CatalogoGeneralResponseDto consumirCatalogo(CatalogoGeneralRequestDto request) {
        Root response = catalogoGeneralRepository.consumirCatalogo(
                request == null ? new CatalogoGeneralRequestDto() : request
        );
        RespuestaDto respuesta = response == null ? null : response.getRespuesta();
        if (respuesta == null) {
            throw new ServiceIntegrationException("La respuesta del servicio de catálogos no tiene el nodo 'respuesta'.");
        }

        CatalogoGeneralResponseDto result = new CatalogoGeneralResponseDto();
        result.setEstatus(respuesta.getEstatus() == null ? 0 : respuesta.getEstatus());
        ErrorDto error = respuesta.getError();
        result.setCodigoError(error == null ? null : error.getCodigoError());
        result.setDescError(error == null ? null : error.getDescError());

        DatosDto datos = respuesta.getDatos();
        result.setRegistros(datos == null ? List.of() : resolveRegistros(datos));
        LOGGER.info("Respuesta catalogoGeneral: {}", result);
        return result;
    }

    private List<CatalogoRegistroDto> resolveRegistros(DatosDto datos) {
        if (datos.getRegistros() != null && !datos.getRegistros().isEmpty()) {
            return datos.getRegistros().stream()
                    .filter(Objects::nonNull)
                    .map(this::toCatalogoRegistro)
                    .filter(this::hasCatalogoData)
                    .toList();
        }
        return List.of();
    }

    private CatalogoRegistroDto toCatalogoRegistro(RegistroDto registro) {
        CatalogoRegistroDto dto = new CatalogoRegistroDto();
        dto.setActivo(registro.getActivo());
        dto.setCodTblRef(registro.getCodTblRef());
        dto.setTotalPaginas(registro.getTotalPaginas());
        dto.setClaveFila(registro.getClaveFila());
        dto.setTotalRegistros(registro.getTotalRegistros());
        dto.setId(registro.getId());
        dto.setDescrLarga(registro.getDescrLarga());
        dto.setCodAplccnSubapl(registro.getCodAplccnSubapl());
        dto.setContenidoFila(registro.getContenidoFila());
        dto.setDescrCorta(registro.getDescrCorta());
        dto.setPaginaActual(registro.getPaginaActual());
        return dto;
    }

    private boolean hasCatalogoData(CatalogoRegistroDto registro) {
        return hasText(registro.getId()) || hasText(registro.getDescrLarga()) || hasText(registro.getCodTblRef());
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
