package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDto {
    @JsonProperty("estatus")
    private Integer estatus;
    @JsonProperty("error")
    private ErrorDto error;
    @JsonProperty("datos")
    private DatosDto datos;
}
