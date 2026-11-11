package mx.gob.babien.tcb.dto;

import java.util.ArrayList;
import java.util.List;

public class ClienteBusquedaResponseDto {
    private int estatus;
    private String codigo;
    private String mensaje;
    private String numTask;
    private String accionAplicada;
    private List<ClienteEncontradoDto> clientes = new ArrayList<>();

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumTask() {
        return numTask;
    }

    public void setNumTask(String numTask) {
        this.numTask = numTask;
    }

    public String getAccionAplicada() {
        return accionAplicada;
    }

    public void setAccionAplicada(String accionAplicada) {
        this.accionAplicada = accionAplicada;
    }

    public List<ClienteEncontradoDto> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEncontradoDto> clientes) {
        this.clientes = clientes;
    }
}
