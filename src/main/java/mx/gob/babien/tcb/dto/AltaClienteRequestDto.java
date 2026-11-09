package mx.gob.babien.tcb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AltaClienteRequestDto {
    @JsonProperty("TARIF-CTA-PASIVO")
    private String tarifCtaPasivo;
    @JsonProperty("ID USUARIO")
     @NotBlank(message = "El campo ID-USUARIO es obligatorio.")
    private String idUsuario;
    @JsonProperty("TPO-ID")
    @NotBlank(message = "El campo TPO-ID es obligatorio.")
    private String tpoId;
    @JsonProperty("NUM-ID")
    @NotBlank(message = "El campo NUM-ID es obligatorio.")
    private String numId;
    @JsonProperty("NUM-SUC")
    @NotBlank(message = "El campo NUM-SUC es obligatorio.")
    private String numSuc;
    @JsonProperty("GENERO")
    private String genero;
    @JsonProperty("NOMBRE")
    @NotBlank(message = "El campo NOMBRE es obligatorio.")
    private String nombre;
    @JsonProperty("PRIM-APE")
    @NotBlank(message = "El campo PRIM-APE es obligatorio.")
    private String primApe;
    @JsonProperty("SEGU-APE")
    private String seguApe;
    @JsonProperty("REG-FISC")
    private String regFisc;
    @JsonProperty("RFC")
    private String rfc;
    @JsonProperty("CURP")
    @NotBlank(message = "El campo CURP es obligatorio.")
    private String curp;
    @JsonProperty("COD-PAIS-NAL")
    private String codPaisNal;
    @JsonProperty("COD-PAIS-RES")
    private String codPaisRes;
    @JsonProperty("COD-CNO")
    private String codCno;
    @JsonProperty("COD-CNAE")
    private String codCnae;
    @JsonProperty("FEC-NACTO")
    @NotBlank(message = "El campo FEC-NACTO es obligatorio.")
    private String fecNacto;
    @JsonProperty("COD-POSTAL-LUG-NACTO")
    private String codPostalLugNacto;
    @JsonProperty("COD-EDO-NAC")
    private String codEdoNac;
    @JsonProperty("COD-PAIS-NACTO")
    private String codPaisNacto;
    @JsonProperty("TRANS-NUM-DEPO")
    private String transNumDepo;
    @JsonProperty("TRANS-IMPT-DEPO")
    private String transImptDepo;
    @JsonProperty("TRANS-NUM-RETI")
    private String transNumReti;
    @JsonProperty("TRANS-IMPT-RETI")
    private String transImptReti;
    @JsonProperty("FTEINGR-HONORARIOS")
    private String fteingrHonorarios;
    @JsonProperty("FTEINGR-SUELDOS")
    private String fteingrSueldos;
    @JsonProperty("FTEINGR-ARRENDAMTO")
    private String fteingrArrendamto;
    @JsonProperty("FTEINGR-ACTV-COMERCIAL")
    private String fteingrActvComercial;
    @JsonProperty("FTEINGR-OTROS")
    private String fteingrOtros;
    @JsonProperty("FTEINGR-DESCRIBA-OTROS")
    private String fteingrDescribaOtros;
    @JsonProperty("FTEINGR-MTO-MEN-INGRESOS")
    private String fteingrMtoMenIngresos;
    @JsonProperty("USOCTA-PAGO-SERVS")
    private String usoctaPagoServs;
    @JsonProperty("USOCTA-OTROS")
    private String usoctaOtros;
    @JsonProperty("USOCTA-DESCRIBA-OTROS")
    private String usoctaDescribaOtros;
    @JsonProperty("OPEREA-PORC-PROPIOS")
    private String opereaPorcPropios;
    @JsonProperty("OPEREA-PORC-TERCEROS")
    private String opereaPorcTerceros;
    @JsonProperty("PERSONA-POLI-EXPUESTA")
    private String personaPoliExpuesta;
    @JsonProperty("PERSONA-POLI-EXPUESTA-DESC")
    private String personaPoliExpuestaDesc;
    @JsonProperty("REL-PERSONA-POLI-EXPUEST")
    private String relPersonaPoliExpuest;
    @JsonProperty("REL-PERSONA-POLI-EXPUEST-DESC")
    private String relPersonaPoliExpuestDesc;
    @JsonProperty("REL-PERSONA-POLI-EXPUEST-NOM")
    private String relPersonaPoliExpuestNom;
    @JsonProperty("DOM-NUM-TELEF-DOM")
    private String domNumTelefDom;
    @JsonProperty("DOM-TIPO-CALLE")
    private String domTipoCalle;
    @JsonProperty("DOM-NOMB-CALLE")
    private String domNombCalle;
    @JsonProperty("DOM-NUM-EXT")
    private String domNumExt;
    @JsonProperty("DOM-NUM-INT")
    private String domNumInt;
    @JsonProperty("DOM-NOMB-COLONIA")
    private String domNombColonia;
    @JsonProperty("DOM-COD-POSTAL")
    private String domCodPostal;
    @JsonProperty("NOMB-CORREO")
    private String nombCorreo;
    @JsonProperty("COD-DOC1")
    private String codDoc1;
    @JsonProperty("DESC-COD1")
    private String descCod1;
    @JsonProperty("COD-DOC2")
    private String codDoc2;
    @JsonProperty("DESC-COD2")
    private String descCod2;
    @JsonProperty("COD-DOC3")
    private String codDoc3;
    @JsonProperty("DESC-COD3")
    private String descCod3;
    @JsonProperty("COD-DOC4")
    private String codDoc4;
    @JsonProperty("DESC-COD4")
    private String descCod4;
    @JsonProperty("OPEREA-DEPOSITOS")
    private String operaDepositos;
    @JsonProperty("OPEREA-RETIROS")
    private String operaRetiros;
    @JsonProperty("OPEREA-COBRO-GIROS")
    private String operaCobroGiros;
    @JsonProperty("OPEREA-TRANSFERENCIAS")
    private String operaTransferencias;
    @JsonProperty("USOCTA-ADM-INGRE-Y-GASTS")
    private String usoctaAdmIngreYGasts;
    @JsonProperty("USOCTA-DEPTO-NOMIN")
    private String usoctaDeptoNomin;
    @JsonProperty("DOM-REG-OCUPACION")
    private String domRegOcupacion;

}
