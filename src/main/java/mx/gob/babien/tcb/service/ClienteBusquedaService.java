package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.ClienteBusquedaResponseDto;

public interface ClienteBusquedaService {
    ClienteBusquedaResponseDto buscarCliente(ClienteBusquedaRequestDto request);
}
