package mx.gob.babien.tcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteBusquedaResponseDto {
    private int estatus;
    private String codigo;
    private String mensaje;
    private String numTask;
    private String accionAplicada;
    private List<ClienteEncontradoDto> clientes = new ArrayList<>();
}
