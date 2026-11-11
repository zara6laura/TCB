package mx.gob.babien.tcb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApat() {
        return apat;
    }

    public void setApat(String apat) {
        this.apat = apat;
    }

    public String getAmat() {
        return amat;
    }

    public void setAmat(String amat) {
        this.amat = amat;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIndPaginacion() {
        return indPaginacion;
    }

    public void setIndPaginacion(String indPaginacion) {
        this.indPaginacion = indPaginacion;
    }

    public String getIdCte() {
        return idCte;
    }

    public void setIdCte(String idCte) {
        this.idCte = idCte;
    }
}
