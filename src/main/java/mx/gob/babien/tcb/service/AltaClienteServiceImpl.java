package mx.gob.babien.tcb.service;

import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import mx.gob.babien.tcb.dto.response.AltaClienteResponseDto;
import mx.gob.babien.tcb.exception.BadRequestException;
import mx.gob.babien.tcb.repository.AltaClienteRepository;
import mx.gob.babien.tcb.util.AltaClienteDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AltaClienteServiceImpl implements AltaClienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AltaClienteServiceImpl.class);
    private final AltaClienteRepository altaClienteRepository;
    private final AltaClienteDtoMapper altaClienteDtoMapper;

    public AltaClienteServiceImpl(
            AltaClienteRepository altaClienteRepository,
            AltaClienteDtoMapper altaClienteDtoMapper
    ) {
        this.altaClienteRepository = altaClienteRepository;
        this.altaClienteDtoMapper = altaClienteDtoMapper;
    }

    @Override
    public AltaClienteResponseDto altaCliente(AltaClienteRequestDto request) {
        AltaClienteRequestDto normalizedRequest = altaClienteDtoMapper.normalize(request);
        validate(normalizedRequest);
        AltaClienteResponseDto response = altaClienteRepository.altaCliente(
                altaClienteDtoMapper.toServiceRequest(normalizedRequest)
        );
        LOGGER.info("Respuesta altaCliente: {}", response);
        return response;
    }

    private void validate(AltaClienteRequestDto request) {
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
