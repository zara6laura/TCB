package mx.gob.babien.tcb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.gob.babien.tcb.dto.request.ClienteBusquedaRequestDto;
import mx.gob.babien.tcb.dto.response.ClienteBusquedaResponseDto;
import mx.gob.babien.tcb.dto.response.Root;
import mx.gob.babien.tcb.repository.ClienteBusquedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ClienteBusquedaServiceImplTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mock
    private ClienteBusquedaRepository clienteBusquedaRepository;

    private ClienteBusquedaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ClienteBusquedaServiceImpl(clienteBusquedaRepository);
        when(clienteBusquedaRepository.buscarCliente(any())).thenReturn(new Root());
    }

    @Test
    void shouldUseAccionNAndEmptyPaginationWhenIdCteIsEmpty() {
        ClienteBusquedaRequestDto request = new ClienteBusquedaRequestDto();
        request.setCurp("pasg800809hdfznb05");
        request.setNombre("Gabriel");
        request.setApat("Paz");
        request.setAmat("Santa Ana");

        service.buscarCliente(request);

        ArgumentCaptor<ClienteBusquedaRequestDto> requestCaptor = ArgumentCaptor.forClass(ClienteBusquedaRequestDto.class);
        verify(clienteBusquedaRepository).buscarCliente(requestCaptor.capture());
        ClienteBusquedaRequestDto outboundRequest = requestCaptor.getValue();
        assertThat(outboundRequest.getAccion()).isEqualTo("N");
        assertThat(outboundRequest.getIndPaginacion()).isEmpty();
    }

    @Test
    void shouldUseAccionCAndPaginationWhenIdCteHasValue() {
        ClienteBusquedaRequestDto request = new ClienteBusquedaRequestDto();
        request.setCurp("PASG800809HDFZNB05");
        request.setNombre("Gabriel");
        request.setApat("Paz");
        request.setIdCte("0023782302");

        service.buscarCliente(request);

        ArgumentCaptor<ClienteBusquedaRequestDto> requestCaptor = ArgumentCaptor.forClass(ClienteBusquedaRequestDto.class);
        verify(clienteBusquedaRepository).buscarCliente(requestCaptor.capture());
        ClienteBusquedaRequestDto outboundRequest = requestCaptor.getValue();
        assertThat(outboundRequest.getAccion()).isEqualTo("C");
        assertThat(outboundRequest.getIndPaginacion()).isEqualTo("N");
    }

    @Test
    void shouldReadClientesFromRespuestaRegistrosWhenEstatusIsZero() throws Exception {
        String rawResponse = """
                {
                  "respuesta": {
                    "estatus": 0,
                    "error": {
                      "codigoError": "SNTEA101",
                      "descError": "CONSULTA EXITOSA, NO HAY MAS DATOS"
                    },
                    "datos": {
                      "registros": [
                        {
                          "CLIENTE": "0023782302",
                          "NOMBRE": "SARA LAURA",
                          "APEPAT": "GONZALEZ",
                          "APEMAT": "BADILLO",
                          "CURP": "GOBS010706MZSNDRA9"
                        }
                      ]
                    }
                  }
                }
                """;
        Root root = OBJECT_MAPPER.readValue(rawResponse, Root.class);
        when(clienteBusquedaRepository.buscarCliente(any())).thenReturn(root);

        ClienteBusquedaResponseDto response = service.buscarCliente(new ClienteBusquedaRequestDto());

        assertThat(response.getEstatus()).isEqualTo(0);
        assertThat(response.getCodigo()).isEqualTo("SNTEA101");
        assertThat(response.getMensaje()).isEqualTo("CONSULTA EXITOSA, NO HAY MAS DATOS");
        assertThat(response.getClientes()).hasSize(1);
        assertThat(response.getClientes().get(0).getIdCte()).isEqualTo("0023782302");
    }

    @Test
    void shouldReturnErrorMessageAndEmptyClientesWhenEstatusIsOne() throws Exception {
        String rawResponse = """
                {
                  "respuesta": {
                    "estatus": 1,
                    "error": {
                      "codigoError": "ARQE0092",
                      "descError": "NO EXISTEN DATOS DE LA PERSONA"
                    },
                    "datos": {
                      "registros": [
                        {
                          "CLIENTE": "0000000000",
                          "NOMBRE": "                              "
                        }
                      ]
                    }
                  }
                }
                """;
        Root root = OBJECT_MAPPER.readValue(rawResponse, Root.class);
        when(clienteBusquedaRepository.buscarCliente(any())).thenReturn(root);

        ClienteBusquedaResponseDto response = service.buscarCliente(new ClienteBusquedaRequestDto());

        assertThat(response.getEstatus()).isEqualTo(1);
        assertThat(response.getCodigo()).isEqualTo("ARQE0092");
        assertThat(response.getMensaje()).isEqualTo("NO EXISTEN DATOS DE LA PERSONA");
        assertThat(response.getClientes()).isEmpty();
    }
}

