package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.CodigoPostalRequestDto;
import mx.gob.babien.tcb.dto.response.Root;

public interface CodigoPostalRepository {
    Root consumirCodigoPostal(CodigoPostalRequestDto request);
}
