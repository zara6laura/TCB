package mx.gob.babien.tcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEncontradoDto {
    private String idCte;
    private String nombre;
    private String apePat;
    private String apeMat;
    private String fecNac;
    private String curp;
    private String estado;
    private String genero;
    private String tipoId;
    private String idIdentificacion;
    private String domicilio;
    private String telefono;
    private String correo;
    private String codigoPostal;
    private String paisNacionalidad;
    private String rfc;
    private String municipio;
    private String fechaAlta;
}