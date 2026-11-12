package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.response.CatalogoGeneralRawResponseDto;
import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;

public interface CatalogoGeneralRepository {
    CatalogoGeneralRawResponseDto consumirCatalogo(CatalogoGeneralRequestDto request);
}
