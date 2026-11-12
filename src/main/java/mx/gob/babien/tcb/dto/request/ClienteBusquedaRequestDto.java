package mx.gob.babien.tcb.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteBusquedaRequestDto {
    @JsonProperty("ACCION")
    private String accion;
    @JsonProperty("CURP")
    private String curp;
    @JsonProperty("NOMBRE")
    private String nombre;
    @JsonProperty("APAT")
    private String apat;
    @JsonProperty("AMAT")
    private String amat;
    @JsonProperty("GENERO")
    private String genero;
    @JsonProperty("INDPAGINACION")
    private String indPaginacion;
    @JsonProperty("IDCTE")
    private String idCte;

}
