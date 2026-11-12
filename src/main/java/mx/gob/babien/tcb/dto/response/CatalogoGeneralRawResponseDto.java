package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class CatalogoGeneralRawResponseDto {
    private RespuestaDto respuesta;

    public RespuestaDto getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDto respuesta) {
        this.respuesta = respuesta;
    }

    public static class RespuestaDto {
        private Integer estatus;
        private ErrorDto error;
        private DatosDto datos;

        public Integer getEstatus() {
            return estatus;
        }

        public void setEstatus(Integer estatus) {
            this.estatus = estatus;
        }

        public ErrorDto getError() {
            return error;
        }

        public void setError(ErrorDto error) {
            this.error = error;
        }

        public DatosDto getDatos() {
            return datos;
        }

        public void setDatos(DatosDto datos) {
            this.datos = datos;
        }
    }

    public static class ErrorDto {
        private String codigoError;
        private String descError;

        public String getCodigoError() {
            return codigoError;
        }

        public void setCodigoError(String codigoError) {
            this.codigoError = codigoError;
        }

        public String getDescError() {
            return descError;
        }

        public void setDescError(String descError) {
            this.descError = descError;
        }
    }

    public static class DatosDto {
        private List<RegistroDto> registros = new ArrayList<>();

        public List<RegistroDto> getRegistros() {
            return registros;
        }

        public void setRegistros(List<RegistroDto> registros) {
            this.registros = registros;
        }
    }

    public static class RegistroDto {
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

        public String getActivo() {
            return activo;
        }

        public void setActivo(String activo) {
            this.activo = activo;
        }

        public String getCodTblRef() {
            return codTblRef;
        }

        public void setCodTblRef(String codTblRef) {
            this.codTblRef = codTblRef;
        }

        public String getTotalPaginas() {
            return totalPaginas;
        }

        public void setTotalPaginas(String totalPaginas) {
            this.totalPaginas = totalPaginas;
        }

        public String getClaveFila() {
            return claveFila;
        }

        public void setClaveFila(String claveFila) {
            this.claveFila = claveFila;
        }

        public String getTotalRegistros() {
            return totalRegistros;
        }

        public void setTotalRegistros(String totalRegistros) {
            this.totalRegistros = totalRegistros;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescrLarga() {
            return descrLarga;
        }

        public void setDescrLarga(String descrLarga) {
            this.descrLarga = descrLarga;
        }

        public String getCodAplccnSubapl() {
            return codAplccnSubapl;
        }

        public void setCodAplccnSubapl(String codAplccnSubapl) {
            this.codAplccnSubapl = codAplccnSubapl;
        }

        public String getContenidoFila() {
            return contenidoFila;
        }

        public void setContenidoFila(String contenidoFila) {
            this.contenidoFila = contenidoFila;
        }

        public String getDescrCorta() {
            return descrCorta;
        }

        public void setDescrCorta(String descrCorta) {
            this.descrCorta = descrCorta;
        }

        public String getPaginaActual() {
            return paginaActual;
        }

        public void setPaginaActual(String paginaActual) {
            this.paginaActual = paginaActual;
        }
    }
}

