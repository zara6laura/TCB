package mx.gob.babien.tcb.repository;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

public interface ClienteBusquedaRepository {
    JsonNode buscarCliente(Map<String, String> payload);
}
