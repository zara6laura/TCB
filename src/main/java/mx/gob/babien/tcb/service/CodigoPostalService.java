package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.request.CodigoPostalRequestDto;
import mx.gob.babien.tcb.dto.response.CodigoPostalResponseDto;

public interface CodigoPostalService {
    CodigoPostalResponseDto consumirCodigoPostal(CodigoPostalRequestDto request);
}
