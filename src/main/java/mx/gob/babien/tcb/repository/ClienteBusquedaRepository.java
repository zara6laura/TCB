package mx.gob.babien.tcb.repository;

import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.response.Root;

public interface ClienteBusquedaRepository {
    Root buscarCliente(ClienteBusquedaRequestDto request);
}
