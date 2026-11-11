package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.AltaClienteResponseDto;
import mx.gob.babien.tcb.exception.BadRequestException;
import mx.gob.babien.tcb.repository.AltaClienteRepository;
import mx.gob.babien.tcb.util.AltaClientePayloadMapper;
import org.springframework.stereotype.Service;

@Service
public class AltaClienteServiceImpl implements AltaClienteService {
    private final AltaClienteRepository altaClienteRepository;
    private final AltaClientePayloadMapper altaClientePayloadMapper;

    public AltaClienteServiceImpl(
            AltaClienteRepository altaClienteRepository,
            AltaClientePayloadMapper altaClientePayloadMapper
    ) {
        this.altaClienteRepository = altaClienteRepository;
        this.altaClientePayloadMapper = altaClientePayloadMapper;
    }

    @Override
    public AltaClienteResponseDto altaCliente(AltaClienteRequestDto request) {
        AltaClienteRequestDto normalizedRequest = altaClientePayloadMapper.normalize(request);
        validate(normalizedRequest);
        return altaClienteRepository.altaCliente(altaClientePayloadMapper.toServicePayload(normalizedRequest));
    }

    private void validate(AltaClienteRequestDto request) {
       // requireField(request.getIdUsuario(), "ID-USUARIO");
        requireField(request.getTpoId(), "TPO-ID");
        requireField(request.getNumId(), "NUM-ID");
        requireField(request.getNumSuc(), "NUM-SUC");
        requireField(request.getNombre(), "NOMBRE");
        requireField(request.getPrimApe(), "PRIM-APE");
        requireField(request.getCurp(), "CURP");
        requireField(request.getFecNacto(), "FEC-NACTO");
    }

    private static void requireField(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException("El campo " + fieldName + " es obligatorio.");
        }
    }

}
