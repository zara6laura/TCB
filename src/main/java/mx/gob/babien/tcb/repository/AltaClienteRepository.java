package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;

public interface AltaClienteRepository {
    AltaClienteResponseDto altaCliente(AltaClienteRequestDto request);
}
