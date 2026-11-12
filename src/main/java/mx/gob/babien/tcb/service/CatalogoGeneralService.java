package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.response.CatalogoGeneralResponseDto;

public interface CatalogoGeneralService {
    CatalogoGeneralResponseDto consumirCatalogo(CatalogoGeneralRequestDto request);
}
