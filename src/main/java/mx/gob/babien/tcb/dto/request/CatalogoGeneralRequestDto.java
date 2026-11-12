package mx.gob.babien.tcb.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoGeneralRequestDto {
    @JsonProperty("COD_TBL_REF")
    private String codTblRef;
    @JsonProperty("COD_APLCCN_SUBAPL")
    private String codAplccnSubapl;
    @JsonProperty("numeropagina")
    private String numeroPagina;

}

