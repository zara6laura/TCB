package mx.gob.babien.tcb.model;

public record CurpData(
        String curp,
        String genero,
        String fechaNacimiento,
        String entidadFederativa,
        String pais
) {
}
