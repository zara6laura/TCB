package mx.gob.babien.tcb.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mx.gob.babien.tcb.dto.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.dto.ClienteEncontradoDto;
import mx.gob.babien.tcb.repository.ClienteBusquedaRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteBusquedaServiceImpl implements ClienteBusquedaService {
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
        JsonNode serviceResponse = clienteBusquedaRepository.buscarCliente(toServicePayload(normalizedRequest));
        return toResponse(serviceResponse);
    }

    private ClienteBusquedaRequestDto normalize(ClienteBusquedaRequestDto source) {
        ClienteBusquedaRequestDto request = source == null ? new ClienteBusquedaRequestDto() : source;
        ClienteBusquedaRequestDto normalized = new ClienteBusquedaRequestDto();
        String curp = normalize(request.getCurp());
        String nombre = normalize(request.getNombre());
        String apat = normalize(request.getApat());
        String amat = normalize(request.getAmat());
        String idCte = firstNonBlank(normalize(request.getIdCte()), DEFAULT_EMPTY);
        boolean hasIdCte = hasText(idCte);
        normalized.setAccion(hasIdCte ? ACCION_CON_ID_CTE : ACCION_SIN_ID_CTE);
        normalized.setCurp(curp);
        normalized.setNombre(nombre);
        normalized.setApat(apat);
        normalized.setAmat(amat);
        normalized.setGenero(normalize(request.getGenero()));
        normalized.setIndPaginacion(
                firstNonBlank(
                        normalize(request.getIndPaginacion()),
                        hasIdCte ? DEFAULT_PAGINACION : DEFAULT_EMPTY
                )
        );
        normalized.setIdCte(idCte);
        return normalized;
    }

    private Map<String, String> toServicePayload(ClienteBusquedaRequestDto request) {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("ACCION", emptyIfNull(request.getAccion()));
        payload.put("CURP", emptyIfNull(request.getCurp()));
        payload.put("NOMBRE", emptyIfNull(request.getNombre()));
        payload.put("APAT", emptyIfNull(request.getApat()));
        payload.put("AMAT", emptyIfNull(request.getAmat()));
        payload.put("GENERO", emptyIfNull(request.getGenero()));
        payload.put("INDPAGINACION", emptyIfNull(request.getIndPaginacion()));
        payload.put("IDCTE", emptyIfNull(request.getIdCte()));
        return payload;
    }

    private ClienteBusquedaResponseDto toResponse(JsonNode serviceResponse) {
        JsonNode resultNode = firstObjectPath(
                serviceResponse,
                "EjecutarResponse", "EjecutarResult"
        );
        JsonNode metadataNode = resultNode == null ? serviceResponse : resultNode;
        ClienteBusquedaResponseDto response = new ClienteBusquedaResponseDto();
        response.setEstatus(readInt(metadataNode, 0, "estatus", "ESTATUS"));
        response.setCodigo(readText(metadataNode, "codigo", "CODIGO"));
        response.setMensaje(readText(metadataNode, "mensaje", "MENSAJE"));
        response.setNumTask(readText(metadataNode, "numTask", "NUMTASK", "NUM_TASK"));
        response.setAccionAplicada(readText(metadataNode, "accionAplicada", "ACCIONAPLICADA", "ACCION_APLICADA"));
        response.setClientes(readClientes(serviceResponse));
        return response;
    }

    private List<ClienteEncontradoDto> readClientes(JsonNode serviceResponse) {
        List<ClienteEncontradoDto> clientes = new ArrayList<>();
        if (serviceResponse == null || serviceResponse.isNull()) {
            return clientes;
        }

        JsonNode rowsNode = resolveRowsNode(serviceResponse);
        if (rowsNode == null || rowsNode.isNull()) {
            return clientes;
        }

        if (rowsNode.isArray()) {
            for (JsonNode row : rowsNode) {
                addClienteIfPresent(clientes, row);
            }
            return clientes;
        }

        if (rowsNode.isObject()) {
            addClienteIfPresent(clientes, rowsNode);
        }
        return clientes;
    }

    private JsonNode resolveRowsNode(JsonNode serviceResponse) {
        if (serviceResponse == null || serviceResponse.isNull()) {
            return null;
        }
        if (serviceResponse.isArray()) {
            return serviceResponse;
        }

        JsonNode directRows = firstArray(serviceResponse, "clientes", "CLIENTES", "resultado", "RESULTADO", "data", "DATA");
        if (directRows != null) {
            return directRows;
        }

        JsonNode resultNode = firstObjectPath(serviceResponse, "EjecutarResponse", "EjecutarResult");
        if (resultNode == null || resultNode.isNull()) {
            return null;
        }

        JsonNode resultRows = firstArray(resultNode, "clientes", "CLIENTES", "resultado", "RESULTADO", "data", "DATA");
        if (resultRows != null) {
            return resultRows;
        }

        JsonNode responseBansefi = firstNode(resultNode, "ResponseBansefi", "responseBansefi", "RESPONSEBANSEFI");
        if (responseBansefi == null || responseBansefi.isNull()) {
            return null;
        }

        if (responseBansefi.isArray()) {
            return responseBansefi;
        }

        if (responseBansefi.isObject()) {
            JsonNode nestedResponseBansefi = firstNode(
                    responseBansefi,
                    "ResponseBansefi", "responseBansefi", "RESPONSEBANSEFI"
            );
            if (nestedResponseBansefi != null && !nestedResponseBansefi.isNull()) {
                return nestedResponseBansefi;
            }
            return responseBansefi;
        }

        return null;
    }

    private void addClienteIfPresent(List<ClienteEncontradoDto> clientes, JsonNode row) {
        if (row == null || row.isNull()) {
            return;
        }
        ClienteEncontradoDto cliente = new ClienteEncontradoDto();
        cliente.setIdCte(readText(row, "idCte", "IDCTE", "ID_CTE", "idInterna", "ID_INTERNA", "ID.INTERNA", "CLIENTE"));
        cliente.setNombre(readText(row, "nombre", "NOMBRE"));
        cliente.setApePat(readText(row, "apePat", "APEPAT", "APAT", "PRIM-APE", "PRIM_APE"));
        cliente.setApeMat(readText(row, "apeMat", "APEMAT", "AMAT", "SEGU-APE", "SEGU_APE"));
        cliente.setFecNac(readText(row, "fecNac", "FECNAC", "FEC-NACTO", "FEC_NACTO"));
        cliente.setCurp(readText(row, "curp", "CURP"));
        cliente.setEstado(readText(row, "estado", "ESTADO", "EDO"));
        cliente.setGenero(readText(row, "genero", "GENERO"));
        cliente.setTipoId(readText(row, "tipoId", "TIPOID", "TPO-ID", "TPO_ID"));
        cliente.setIdIdentificacion(readText(row, "idIdentificacion", "IDIDENTIFICACION", "NUM-ID", "NUM_ID", "IDEXTPE"));
        cliente.setDomicilio(readText(row, "domicilio", "DOMICILIO", "DOMIC50"));
        cliente.setTelefono(readText(row, "telefono", "TELEFONO", "TEL", "NUMTELDOM"));
        cliente.setCorreo(readText(row, "correo", "CORREO"));
        cliente.setCodigoPostal(readText(row, "codigoPostal", "CODIGOPOSTAL", "CP", "COD-POSTAL", "CODPOST"));
        cliente.setPaisNacionalidad(readText(row, "paisNacionalidad", "PAISNACIONALIDAD", "COD-PAIS-NAL", "PAISNACTO"));
        cliente.setRfc(readText(row, "rfc", "RFC"));
        cliente.setMunicipio(readText(row, "municipio", "MUNICIPIO"));
        cliente.setFechaAlta(readText(row, "fechaAlta", "FECHAALTA", "FECALTA"));
        if (!hasText(cliente.getIdCte()) && !hasText(cliente.getCurp()) && !hasText(cliente.getNombre())) {
            return;
        }
        clientes.add(cliente);
    }

    private JsonNode firstArray(JsonNode node, String... fieldNames) {
        if (node == null || node.isNull()) {
            return null;
        }
        for (String fieldName : fieldNames) {
            JsonNode candidate = node.get(fieldName);
            if (candidate != null && candidate.isArray()) {
                return candidate;
            }
        }
        return null;
    }

    private JsonNode firstNode(JsonNode node, String... fieldNames) {
        if (node == null || node.isNull()) {
            return null;
        }
        for (String fieldName : fieldNames) {
            JsonNode candidate = node.get(fieldName);
            if (candidate != null && !candidate.isNull()) {
                return candidate;
            }
        }
        return null;
    }

    private JsonNode firstObjectPath(JsonNode root, String... path) {
        JsonNode current = root;
        for (String fieldName : path) {
            if (current == null || current.isNull() || !current.isObject()) {
                return null;
            }
            current = current.get(fieldName);
        }
        return current;
    }

    private String readText(JsonNode node, String... fieldNames) {
        if (node == null || node.isNull()) {
            return null;
        }
        for (String fieldName : fieldNames) {
            JsonNode value = node.get(fieldName);
            if (value == null || value.isNull()) {
                continue;
            }
            String text = value.asText();
            if (text != null && !text.isBlank()) {
                return text.trim();
            }
        }
        return null;
    }

    private int readInt(JsonNode node, int defaultValue, String... fieldNames) {
        String value = readText(node, fieldNames);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        return normalized.toUpperCase(Locale.ROOT);
    }

    private static String emptyIfNull(String value) {
        return value == null ? "" : value;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
