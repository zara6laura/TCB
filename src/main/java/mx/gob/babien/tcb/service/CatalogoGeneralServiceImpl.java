package mx.gob.babien.tcb.service;

import java.util.ArrayList;
import java.util.List;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralRawResponseDto;
import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralResponseDto;
import mx.gob.babien.tcb.dto.response.CatalogoRegistroDto;
import mx.gob.babien.tcb.exception.ServiceIntegrationException;
import mx.gob.babien.tcb.repository.CatalogoGeneralRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogoGeneralServiceImpl implements CatalogoGeneralService {
    private final CatalogoGeneralRepository catalogoGeneralRepository;

    public CatalogoGeneralServiceImpl(CatalogoGeneralRepository catalogoGeneralRepository) {
        this.catalogoGeneralRepository = catalogoGeneralRepository;
    }

    @Override
    public CatalogoGeneralResponseDto consumirCatalogo(CatalogoGeneralRequestDto request) {
        CatalogoGeneralRequestDto safeRequest = request == null ? new CatalogoGeneralRequestDto() : request;
        CatalogoGeneralRawResponseDto response = catalogoGeneralRepository.consumirCatalogo(safeRequest);
        CatalogoGeneralRawResponseDto.RespuestaDto respuesta = response == null ? null : response.getRespuesta();
        if (respuesta == null) {
            throw new ServiceIntegrationException("La respuesta del servicio de catálogos no tiene el nodo 'respuesta'.");
        }

        CatalogoGeneralResponseDto result = new CatalogoGeneralResponseDto();
        result.setEstatus(respuesta.getEstatus() == null ? 0 : respuesta.getEstatus());
        CatalogoGeneralRawResponseDto.ErrorDto error = respuesta.getError();
        result.setCodigoError(error == null ? null : error.getCodigoError());
        result.setDescError(error == null ? null : error.getDescError());

        CatalogoGeneralRawResponseDto.DatosDto datos = respuesta.getDatos();
        result.setRegistros(readRegistros(datos == null ? null : datos.getRegistros()));
        return result;
    }

    private List<CatalogoRegistroDto> readRegistros(List<CatalogoGeneralRawResponseDto.RegistroDto> rawRegistros) {
        List<CatalogoRegistroDto> registros = new ArrayList<>();
        if (rawRegistros == null) {
            return registros;
        }
        for (CatalogoGeneralRawResponseDto.RegistroDto row : rawRegistros) {
            if (row == null) {
                continue;
            }
            registros.add(toRegistro(row));
        }
        return registros;
    }

    private CatalogoRegistroDto toRegistro(CatalogoGeneralRawResponseDto.RegistroDto row) {
        CatalogoRegistroDto dto = new CatalogoRegistroDto();
        dto.setActivo(row.getActivo());
        dto.setCodTblRef(row.getCodTblRef());
        dto.setTotalPaginas(row.getTotalPaginas());
        dto.setClaveFila(row.getClaveFila());
        dto.setTotalRegistros(row.getTotalRegistros());
        dto.setId(row.getId());
        dto.setDescrLarga(row.getDescrLarga());
        dto.setCodAplccnSubapl(row.getCodAplccnSubapl());
        dto.setContenidoFila(row.getContenidoFila());
        dto.setDescrCorta(row.getDescrCorta());
        dto.setPaginaActual(row.getPaginaActual());
        return dto;
    }
}
