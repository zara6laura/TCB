package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosDto {
    @JsonProperty("registros")
    private List<RegistroDto> registros = new ArrayList<>();
}
