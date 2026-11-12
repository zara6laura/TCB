package mx.gob.babien.tcb.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosDto {
    private List<ClienteEncontradoDto> clientes;
}

