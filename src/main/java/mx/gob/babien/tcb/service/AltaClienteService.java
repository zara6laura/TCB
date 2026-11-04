package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;

public interface AltaClienteService {
    AltaClienteResponseDto altaCliente(AltaClienteRequestDto request);
}
