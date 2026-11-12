package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoRegistroDto {
    @JsonProperty("ACTIVO")
    private String activo;
    @JsonProperty("COD_TBL_REF")
    private String codTblRef;
    @JsonProperty("TotalPaginas")
    private String totalPaginas;
    @JsonProperty("CLAVE_FILA")
    private String claveFila;
    @JsonProperty("TotalRegistros")
    private String totalRegistros;
    @JsonProperty("ID")
    private String id;
    @JsonProperty("DESCR_LARGA")
    private String descrLarga;
    @JsonProperty("COD_APLCCN_SUBAPL")
    private String codAplccnSubapl;
    @JsonProperty("CONTENIDO_FILA")
    private String contenidoFila;
    @JsonProperty("DESCR_CORTA")
    private String descrCorta;
    @JsonProperty("PaginaActual")
    private String paginaActual;

}

