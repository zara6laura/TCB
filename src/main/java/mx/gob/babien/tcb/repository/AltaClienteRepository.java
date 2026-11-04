package mx.gob.babien.tcb.repository;

import java.util.Map;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;

public interface AltaClienteRepository {
    AltaClienteResponseDto altaCliente(Map<String, String> payload);
}
