package mx.gob.babien.tcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoGeneralResponseDto {
    private int estatus;
    private String codigoError;
    private String descError;
    private List<CatalogoRegistroDto> registros = new ArrayList<>();
}
