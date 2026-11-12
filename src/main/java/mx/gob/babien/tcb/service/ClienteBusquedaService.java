package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.response.ClienteBusquedaResponseDto;

public interface ClienteBusquedaService {
    ClienteBusquedaResponseDto buscarCliente(ClienteBusquedaRequestDto request);
}
