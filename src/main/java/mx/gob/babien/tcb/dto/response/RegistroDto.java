package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDto {
    @JsonProperty("idCte")
    @JsonAlias({"CLIENTE", "IDCTE", "ID_CTE", "ID_INTERNA", "ID.INTERNA"})
    private String idCte;
    @JsonProperty("nombre")
    @JsonAlias({"NOMBRE"})
    private String nombre;
    @JsonProperty("apePat")
    @JsonAlias({"APEPAT", "APAT", "PRIM-APE", "PRIM_APE"})
    private String apePat;
    @JsonProperty("apeMat")
    @JsonAlias({"APEMAT", "AMAT", "SEGU-APE", "SEGU_APE"})
    private String apeMat;
    @JsonProperty("fecNac")
    @JsonAlias({"FECNAC", "FEC-NACTO", "FEC_NACTO"})
    private String fecNac;
    @JsonProperty("curp")
    @JsonAlias({"CURP"})
    private String curp;
    @JsonProperty("estado")
    @JsonAlias({"ESTADO", "EDO"})
    private String estado;
    @JsonProperty("genero")
    @JsonAlias({"GENERO"})
    private String genero;
    @JsonProperty("tipoId")
    @JsonAlias({"TIPOID", "TPO-ID", "TPO_ID"})
    private String tipoId;
    @JsonProperty("idIdentificacion")
    @JsonAlias({"IDIDENTIFICACION", "NUM-ID", "NUM_ID", "IDEXTPE"})
    private String idIdentificacion;
    @JsonProperty("domicilio")
    @JsonAlias({"DOMICILIO", "DOMIC50"})
    private String domicilio;
    @JsonProperty("telefono")
    @JsonAlias({"TELEFONO", "TEL", "NUMTELDOM"})
    private String telefono;
    @JsonProperty("correo")
    @JsonAlias({"CORREO"})
    private String correo;
    @JsonProperty("codigoPostal")
    @JsonAlias({"CODIGOPOSTAL", "CP", "COD-POSTAL", "CODPOST", "CODPOSTAL"})
    private String codigoPostal;
    @JsonProperty("paisNacionalidad")
    @JsonAlias({"PAISNACIONALIDAD", "COD-PAIS-NAL", "PAISNACTO"})
    private String paisNacionalidad;
    @JsonProperty("rfc")
    @JsonAlias({"RFC"})
    private String rfc;
    @JsonProperty("municipio")
    @JsonAlias({"MUNICIPIO"})
    private String municipio;
    @JsonProperty("fechaAlta")
    @JsonAlias({"FECHAALTA", "FECALTA"})
    private String fechaAlta;
    @JsonProperty("perfil")
    @JsonAlias({"PERFIL", "profile"})
    private String perfil;
    @JsonProperty("idInterna")
    @JsonAlias({"ID_INTERNA", "ID.INTERNA", "id.interna"})
    private String idInterna;
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
