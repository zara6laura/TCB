package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.CatalogoGeneralRequestDto;
import mx.gob.babien.tcb.dto.response.Root;

public interface CatalogoGeneralRepository {
    Root consumirCatalogo(CatalogoGeneralRequestDto request);
}
