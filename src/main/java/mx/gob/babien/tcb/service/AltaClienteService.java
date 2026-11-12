package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;

public interface AltaClienteService {
    AltaClienteResponseDto altaCliente(AltaClienteRequestDto request);
}
