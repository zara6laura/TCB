package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodigoPostalRegistroDto {
    @JsonProperty("CODPOSTAL")
    private String codPostal;
    @JsonProperty("CODLOCALIDAD")
    private String codLocalidad;
    @JsonProperty("NOMLOCALIDAD")
    private String nomLocalidad;
    @JsonProperty("CODMNCPIO")
    private String codMncpio;
    @JsonProperty("NOMMNCPIO")
    private String nomMncpio;
    @JsonProperty("CODCIUDAD")
    private String codCiudad;
    @JsonProperty("NOMCIUDAD")
    private String nomCiudad;
    @JsonProperty("CODESTADO")
    private String codEstado;
    @JsonProperty("NOMESTADO")
    private String nomEstado;
    @JsonProperty("CODPAIS")
    private String codPais;
    @JsonProperty("NOMPAIS")
    private String nomPais;
}
