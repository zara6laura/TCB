package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    @JsonProperty("codigoError")
    private String codigoError;
    @JsonProperty("descError")
    private String descError;
}
