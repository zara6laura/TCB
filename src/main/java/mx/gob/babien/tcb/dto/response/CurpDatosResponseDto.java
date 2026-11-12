package mx.gob.babien.tcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurpDatosResponseDto {
    private String curp;
    private String genero;
    private String fechaNacimiento;
    private String entidadFederativa;
    private String pais;
}
