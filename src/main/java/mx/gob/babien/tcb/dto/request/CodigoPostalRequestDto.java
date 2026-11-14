package mx.gob.babien.tcb.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodigoPostalRequestDto {
    @JsonProperty("CODPOSTAL")
    private String codPostal;
}
