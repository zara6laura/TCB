package mx.gob.babien.tcb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import mx.gob.babien.tcb.dto.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.repository.ClienteBusquedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ClienteBusquedaServiceImplTest {

    @Mock
    private ClienteBusquedaRepository clienteBusquedaRepository;

    private ClienteBusquedaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ClienteBusquedaServiceImpl(clienteBusquedaRepository);
        when(clienteBusquedaRepository.buscarCliente(anyMap())).thenReturn(new ObjectMapper().createObjectNode());
    }

    @Test
    void shouldUseAccionNAndEmptyPaginationWhenIdCteIsEmpty() {
        ClienteBusquedaRequestDto request = new ClienteBusquedaRequestDto();
        request.setCurp("pasg800809hdfznb05");
        request.setNombre("Gabriel");
        request.setApat("Paz");
        request.setAmat("Santa Ana");

        service.buscarCliente(request);

        ArgumentCaptor<Map<String, String>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        verify(clienteBusquedaRepository).buscarCliente(payloadCaptor.capture());
        Map<String, String> payload = payloadCaptor.getValue();
        assertThat(payload.get("ACCION")).isEqualTo("N");
        assertThat(payload.get("INDPAGINACION")).isEmpty();
    }

    @Test
    void shouldUseAccionCAndPaginationWhenIdCteHasValue() {
        ClienteBusquedaRequestDto request = new ClienteBusquedaRequestDto();
        request.setCurp("PASG800809HDFZNB05");
        request.setNombre("Gabriel");
        request.setApat("Paz");
        request.setIdCte("0023782302");

        service.buscarCliente(request);

        ArgumentCaptor<Map<String, String>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        verify(clienteBusquedaRepository).buscarCliente(payloadCaptor.capture());
        Map<String, String> payload = payloadCaptor.getValue();
        assertThat(payload.get("ACCION")).isEqualTo("C");
        assertThat(payload.get("INDPAGINACION")).isEqualTo("N");
    }

    @Test
    void shouldReadNestedBuctenomResponseClients() throws Exception {
        String rawResponse = """
                {
                  "EjecutarResponse": {
                    "EjecutarResult": {
                      "ESTATUS": 0,
                      "CODIGO": "SNTEA101",
                      "MENSAJE": "CONSULTA EXITOSA",
                      "NUMTASK": "0419170023387",
                      "ResponseBansefi": {
                        "ResponseBansefi": {
                          "CLIENTE": "0023782302",
                          "NOMBRE": "SARA LAURA",
                          "APEPAT": "GONZALEZ",
                          "APEMAT": "BADILLO",
                          "FECNAC": "2001-07-06",
                          "CURP": "GOBS010706MZSNDRA9",
                          "EDO": "DISTRITO FEDERAL",
                          "GENERO": "M"
                        }
                      }
                    }
                  }
                }
                """;

        when(clienteBusquedaRepository.buscarCliente(anyMap()))
                .thenReturn(new ObjectMapper().readTree(rawResponse));

        ClienteBusquedaResponseDto response = service.buscarCliente(new ClienteBusquedaRequestDto());

        assertThat(response.getCodigo()).isEqualTo("SNTEA101");
        assertThat(response.getClientes()).hasSize(1);
        assertThat(response.getClientes().get(0).getIdCte()).isEqualTo("0023782302");
        assertThat(response.getClientes().get(0).getCurp()).isEqualTo("GOBS010706MZSNDRA9");
    }
}
