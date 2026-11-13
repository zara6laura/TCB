package mx.gob.babien.tcb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.response.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.dto.response.ClienteEncontradoDto;
import mx.gob.babien.tcb.dto.response.DatosDto;
import mx.gob.babien.tcb.dto.response.ErrorDto;
import mx.gob.babien.tcb.dto.response.RegistroDto;
import mx.gob.babien.tcb.dto.response.RespuestaDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.repository.ClienteBusquedaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClienteBusquedaServiceImpl implements ClienteBusquedaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteBusquedaServiceImpl.class);
    private static final String ACCION_SIN_ID_CTE = "N";
    private static final String ACCION_CON_ID_CTE = "C";
    private static final String DEFAULT_PAGINACION = "N";
    private static final String DEFAULT_EMPTY = "";

    private final ClienteBusquedaRepository clienteBusquedaRepository;

    public ClienteBusquedaServiceImpl(ClienteBusquedaRepository clienteBusquedaRepository) {
        this.clienteBusquedaRepository = clienteBusquedaRepository;
    }

    @Override
    public ClienteBusquedaResponseDto buscarCliente(ClienteBusquedaRequestDto request) {
        ClienteBusquedaRequestDto normalizedRequest = normalize(request);
        Root root = clienteBusquedaRepository.buscarCliente(normalizedRequest);
        ClienteBusquedaResponseDto response = toResponse(root);
        LOGGER.info("Respuesta buscarCliente: {}", response);
        return response;
    }

    private ClienteBusquedaRequestDto normalize(ClienteBusquedaRequestDto source) {
        ClienteBusquedaRequestDto request = source == null ? new ClienteBusquedaRequestDto() : source;
        ClienteBusquedaRequestDto normalized = new ClienteBusquedaRequestDto();
        String curp = normalizeOrEmpty(request.getCurp());
        String nombre = normalizeOrEmpty(request.getNombre());
        String apat = normalizeOrEmpty(request.getApat());
        String amat = normalizeOrEmpty(request.getAmat());
        String idCte = normalizeOrEmpty(request.getIdCte());
        boolean hasIdCte = hasText(idCte);
        normalized.setAccion(hasIdCte ? ACCION_CON_ID_CTE : ACCION_SIN_ID_CTE);
        normalized.setCurp(curp);
        normalized.setNombre(nombre);
        normalized.setApat(apat);
        normalized.setAmat(amat);
        normalized.setGenero(normalizeOrEmpty(request.getGenero()));
        String indPaginacion = normalize(request.getIndPaginacion());
        if (indPaginacion == null) {
            indPaginacion = hasIdCte ? DEFAULT_PAGINACION : DEFAULT_EMPTY;
        }
        normalized.setIndPaginacion(indPaginacion);
        normalized.setIdCte(idCte);
        return normalized;
    }

    private ClienteBusquedaResponseDto toResponse(Root root) {
        ClienteBusquedaResponseDto response = new ClienteBusquedaResponseDto();
        if (root == null || root.getRespuesta() == null) {
            response.setClientes(emptyClientes());
            return response;
        }

        RespuestaDto respuesta = root.getRespuesta();
        ErrorDto error = respuesta.getError();
        DatosDto datos = respuesta.getDatos();

        int estatus = respuesta.getEstatus() == null ? 0 : respuesta.getEstatus();
        response.setEstatus(estatus);
        response.setCodigo(error == null ? null : normalize(error.getCodigoError()));
        response.setMensaje(error == null ? null : normalize(error.getDescError()));
        response.setNumTask(null);
        response.setAccionAplicada(null);

        if (estatus != 0 || datos == null) {
            response.setClientes(emptyClientes());
            return response;
        }

        response.setClientes(fromRegistros(datos.getRegistros()));
        return response;
    }

    private List<ClienteEncontradoDto> fromRegistros(List<RegistroDto> registros) {
        if (registros == null || registros.isEmpty()) {
            return emptyClientes();
        }
        return registros.stream()
                .map(this::toClienteNormalizado)
                .filter(Objects::nonNull)
                .toList();
    }

    private ClienteEncontradoDto toClienteNormalizado(RegistroDto registro) {
        if (registro == null) {
            return null;
        }
        ClienteEncontradoDto cliente = new ClienteEncontradoDto();
        cliente.setIdCte(normalize(registro.getIdCte()));
        cliente.setNombre(normalize(registro.getNombre()));
        cliente.setApePat(normalize(registro.getApePat()));
        cliente.setApeMat(normalize(registro.getApeMat()));
        cliente.setFecNac(normalize(registro.getFecNac()));
        cliente.setCurp(normalize(registro.getCurp()));
        cliente.setEstado(normalize(registro.getEstado()));
        cliente.setGenero(normalize(registro.getGenero()));
        cliente.setTipoId(normalize(registro.getTipoId()));
        cliente.setIdIdentificacion(normalize(registro.getIdIdentificacion()));
        cliente.setDomicilio(normalize(registro.getDomicilio()));
        cliente.setTelefono(normalize(registro.getTelefono()));
        cliente.setCorreo(normalize(registro.getCorreo()));
        cliente.setCodigoPostal(normalize(registro.getCodigoPostal()));
        cliente.setPaisNacionalidad(normalize(registro.getPaisNacionalidad()));
        cliente.setRfc(normalize(registro.getRfc()));
        cliente.setMunicipio(normalize(registro.getMunicipio()));
        cliente.setFechaAlta(normalize(registro.getFechaAlta()));
        if (!hasText(cliente.getIdCte()) && !hasText(cliente.getCurp()) && !hasText(cliente.getNombre())) {
            return null;
        }
        return cliente;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized.toUpperCase(Locale.ROOT);
    }

    private static String normalizeOrEmpty(String value) {
        String normalized = normalize(value);
        return normalized == null ? DEFAULT_EMPTY : normalized;
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static List<ClienteEncontradoDto> emptyClientes() {
        return new ArrayList<>();
    }

}
