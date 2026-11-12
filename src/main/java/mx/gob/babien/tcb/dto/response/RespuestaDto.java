package mx.gob.babien.tcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDto {
    private Integer estatus;
    private ErrorDto error;
    private DatosDto datos;
}

