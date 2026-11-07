package mx.gob.babien.tcb.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import mx.gob.babien.tcb.dto.AltaClienteRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AltaClientePayloadMapper {

    public AltaClienteRequestDto normalize(AltaClienteRequestDto source) {
        AltaClienteRequestDto request = source == null ? new AltaClienteRequestDto() : source;
        AltaClienteRequestDto normalized = new AltaClienteRequestDto();
        normalized.setTarifCtaPasivo(normalize(request.getTarifCtaPasivo(), true));
        normalized.setIdUsuario(normalize(request.getIdUsuario(), true));
        normalized.setTpoId(normalize(request.getTpoId(), true));
        normalized.setNumId(normalize(request.getNumId(), true));
        normalized.setNumSuc(normalize(request.getNumSuc(), true));
        normalized.setGenero(normalize(request.getGenero(), true));
        normalized.setNombre(normalize(request.getNombre(), true));
        normalized.setPrimApe(normalize(request.getPrimApe(), true));
        normalized.setSeguApe(normalize(request.getSeguApe(), true));
        normalized.setRegFisc(normalize(request.getRegFisc(), true));
        normalized.setRfc(normalize(request.getRfc(), true));
        normalized.setCurp(normalize(request.getCurp(), true));
        normalized.setCodPaisNal(normalize(request.getCodPaisNal(), true));
        normalized.setCodPaisRes(normalize(request.getCodPaisRes(), true));
        normalized.setCodCno(normalize(request.getCodCno(), true));
        normalized.setCodCnae(normalize(request.getCodCnae(), true));
        normalized.setFecNacto(normalize(request.getFecNacto(), false));
        normalized.setCodPostalLugNacto(normalize(request.getCodPostalLugNacto(), true));
        normalized.setCodEdoNac(normalize(request.getCodEdoNac(), true));
        normalized.setCodPaisNacto(normalize(request.getCodPaisNacto(), true));
        normalized.setTransNumDepo(normalize(request.getTransNumDepo(), false));
        normalized.setTransImptDepo(normalize(request.getTransImptDepo(), false));
        normalized.setTransNumReti(normalize(request.getTransNumReti(), false));
        normalized.setTransImptReti(normalize(request.getTransImptReti(), false));
        normalized.setFteingrHonorarios(normalize(request.getFteingrHonorarios(), false));
        normalized.setFteingrSueldos(normalize(request.getFteingrSueldos(), false));
        normalized.setFteingrArrendamto(normalize(request.getFteingrArrendamto(), false));
        normalized.setFteingrActvComercial(normalize(request.getFteingrActvComercial(), false));
        normalized.setFteingrOtros(normalize(request.getFteingrOtros(), false));
        normalized.setFteingrDescribaOtros(normalize(request.getFteingrDescribaOtros(), true));
        normalized.setFteingrMtoMenIngresos(normalize(request.getFteingrMtoMenIngresos(), false));
        normalized.setUsoctaPagoServs(normalize(request.getUsoctaPagoServs(), false));
        normalized.setUsoctaOtros(normalize(request.getUsoctaOtros(), false));
        normalized.setUsoctaDescribaOtros(normalize(request.getUsoctaDescribaOtros(), true));
        normalized.setOpereaPorcPropios(normalize(request.getOpereaPorcPropios(), false));
        normalized.setOpereaPorcTerceros(normalize(request.getOpereaPorcTerceros(), false));
        normalized.setPersonaPoliExpuesta(normalize(request.getPersonaPoliExpuesta(), true));
        normalized.setPersonaPoliExpuestaDesc(normalize(request.getPersonaPoliExpuestaDesc(), true));
        normalized.setRelPersonaPoliExpuest(normalize(request.getRelPersonaPoliExpuest(), true));
        normalized.setRelPersonaPoliExpuestDesc(normalize(request.getRelPersonaPoliExpuestDesc(), true));
        normalized.setRelPersonaPoliExpuestNom(normalize(request.getRelPersonaPoliExpuestNom(), true));
        normalized.setDomNumTelefDom(normalize(request.getDomNumTelefDom(), false));
        normalized.setDomTipoCalle(normalize(request.getDomTipoCalle(), true));
        normalized.setDomNombCalle(normalize(request.getDomNombCalle(), true));
        normalized.setDomNumExt(normalize(request.getDomNumExt(), true));
        normalized.setDomNumInt(normalize(request.getDomNumInt(), true));
        normalized.setDomNombColonia(normalize(request.getDomNombColonia(), true));
        normalized.setDomCodPostal(normalize(request.getDomCodPostal(), false));
        normalized.setNombCorreo(normalize(request.getNombCorreo(), false));
        normalized.setCodDoc1(normalizeDocumentValue(request.getCodDoc1(), true));
        normalized.setDescCod1(normalizeDocumentValue(request.getDescCod1(), true));
        normalized.setCodDoc2(normalizeDocumentValue(request.getCodDoc2(), true));
        normalized.setDescCod2(normalizeDocumentValue(request.getDescCod2(), true));
        normalized.setCodDoc3(normalizeDocumentValue(request.getCodDoc3(), true));
        normalized.setDescCod3(normalizeDocumentValue(request.getDescCod3(), true));
        normalized.setCodDoc4(normalizeDocumentValue(request.getCodDoc4(), true));
        normalized.setDescCod4(normalizeDocumentValue(request.getDescCod4(), true));
        normalized.setDomRegOcupacion(normalize(request.getDomRegOcupacion(), false));
        return normalized;
    }

    public Map<String, String> toServicePayload(AltaClienteRequestDto request) {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("TARIF-CTA-PASIVO", emptyIfNull(request.getTarifCtaPasivo()));
        payload.put("ID USUARIO", emptyIfNull(request.getIdUsuario()));
        payload.put("TPO-ID", emptyIfNull(request.getTpoId()));
        payload.put("NUM-ID", emptyIfNull(request.getNumId()));
        payload.put("NUM-SUC", emptyIfNull(request.getNumSuc()));
        payload.put("GENERO", emptyIfNull(request.getGenero()));
        payload.put("NOMBRE", emptyIfNull(request.getNombre()));
        payload.put("PRIM-APE", emptyIfNull(request.getPrimApe()));
        payload.put("SEGU-APE", emptyIfNull(request.getSeguApe()));
        payload.put("REG-FISC", emptyIfNull(request.getRegFisc()));
        payload.put("RFC", emptyIfNull(request.getRfc()));
        payload.put("CURP", emptyIfNull(request.getCurp()));
        payload.put("COD-PAIS-NAL", emptyIfNull(request.getCodPaisNal()));
        payload.put("COD-PAIS-RES", emptyIfNull(request.getCodPaisRes()));
        payload.put("COD-CNO", emptyIfNull(request.getCodCno()));
        payload.put("COD-CNAE", emptyIfNull(request.getCodCnae()));
        payload.put("FEC-NACTO", emptyIfNull(request.getFecNacto()));
        payload.put("COD-POSTAL-LUG-NACTO", emptyIfNull(request.getCodPostalLugNacto()));
        payload.put("COD-EDO-NAC", emptyIfNull(request.getCodEdoNac()));
        payload.put("COD-PAIS-NACTO", emptyIfNull(request.getCodPaisNacto()));
        payload.put("TRANS-NUM-DEPO", emptyIfNull(request.getTransNumDepo()));
        payload.put("TRANS-IMPT-DEPO", emptyIfNull(request.getTransImptDepo()));
        payload.put("TRANS-NUM-RETI", emptyIfNull(request.getTransNumReti()));
        payload.put("TRANS-IMPT-RETI", emptyIfNull(request.getTransImptReti()));
        payload.put("FTEINGR-HONORARIOS", emptyIfNull(request.getFteingrHonorarios()));
        payload.put("FTEINGR-SUELDOS", emptyIfNull(request.getFteingrSueldos()));
        payload.put("FTEINGR-ARRENDAMTO", emptyIfNull(request.getFteingrArrendamto()));
        payload.put("FTEINGR-ACTV-COMERCIAL", emptyIfNull(request.getFteingrActvComercial()));
        payload.put("FTEINGR-OTROS", emptyIfNull(request.getFteingrOtros()));
        payload.put("FTEINGR-DESCRIBA-OTROS", emptyIfNull(request.getFteingrDescribaOtros()));
        payload.put("FTEINGR-MTO-MEN-INGRESOS", emptyIfNull(request.getFteingrMtoMenIngresos()));
        payload.put("USOCTA-ADM-INGRE-Y-GASTS", emptyIfNull(request.getUsoctaAdmIngreYGasts()));
        payload.put("USOCTA-PAGO-SERVS", emptyIfNull(request.getUsoctaPagoServs()));
        payload.put("USOCTA-DEPTO-NOMIN", emptyIfNull(request.getUsoctaDeptoNomin()));
        payload.put("USOCTA-OTROS", emptyIfNull(request.getUsoctaOtros()));
        payload.put("USOCTA-DESCRIBA-OTROS", emptyIfNull(request.getUsoctaDescribaOtros()));
        payload.put("OPEREA-PORC-PROPIOS", emptyIfNull(request.getOpereaPorcPropios()));
        payload.put("OPEREA-PORC-TERCEROS", emptyIfNull(request.getOpereaPorcTerceros()));
        payload.put("PERSONA-POLI-EXPUESTA", emptyIfNull(request.getPersonaPoliExpuesta()));
        payload.put("PERSONA-POLI-EXPUESTA-DESC", emptyIfNull(request.getPersonaPoliExpuestaDesc()));
        payload.put("REL-PERSONA-POLI-EXPUEST", emptyIfNull(request.getRelPersonaPoliExpuest()));
        payload.put("REL-PERSONA-POLI-EXPUEST-DESC", emptyIfNull(request.getRelPersonaPoliExpuestDesc()));
        payload.put("REL-PERSONA-POLI-EXPUEST-NOM", emptyIfNull(request.getRelPersonaPoliExpuestNom()));
        payload.put("DOM-NUM-TELEF-DOM", emptyIfNull(request.getDomNumTelefDom()));
        payload.put("DOM-TIPO-CALLE", emptyIfNull(request.getDomTipoCalle()));
        payload.put("DOM-NOMB-CALLE", emptyIfNull(request.getDomNombCalle()));
        payload.put("DOM-NUM-EXT", emptyIfNull(request.getDomNumExt()));
        payload.put("DOM-NUM-INT", emptyIfNull(request.getDomNumInt()));
        payload.put("DOM-NOMB-COLONIA", emptyIfNull(request.getDomNombColonia()));
        payload.put("DOM-COD-POSTAL", emptyIfNull(request.getDomCodPostal()));
        payload.put("NOMB-CORREO", emptyIfNull(request.getNombCorreo()));
        payload.put("COD-DOC1", emptyIfNull(request.getCodDoc1()));
        payload.put("DESC-COD1", emptyIfNull(request.getDescCod1()));
        payload.put("COD-DOC2", emptyIfNull(request.getCodDoc2()));
        payload.put("DESC-COD2", emptyIfNull(request.getDescCod2()));
        payload.put("COD-DOC3", emptyIfNull(request.getCodDoc3()));
        payload.put("DESC-COD3", emptyIfNull(request.getDescCod3()));
        payload.put("COD-DOC4", emptyIfNull(request.getCodDoc4()));
        payload.put("DESC-COD4", emptyIfNull(request.getDescCod4()));
        payload.put("OPEREA-DEPOSITOS", emptyIfNull(request.getOperaDepositos()));
        payload.put("OPEREA-RETIROS", emptyIfNull(request.getOperaRetiros()));
        payload.put("OPEREA-COBRO-GIROS", emptyIfNull(request.getOperaCobroGiros()));
        payload.put("OPEREA-TRANSFERENCIAS", emptyIfNull(request.getOperaTransferencias()));
        payload.put("DOM-REG-OCUPACION", emptyIfNull(request.getDomRegOcupacion()));
        return payload;
    }

    private static String normalize(String value, boolean uppercase) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        return uppercase ? normalized.toUpperCase(Locale.ROOT) : normalized;
    }

    private static String normalizeDocumentValue(String value, boolean uppercase) {
        String normalized = normalize(value, uppercase);
        return normalized == null ? " " : normalized;
    }

    private static String emptyIfNull(String value) {
        return value == null ? "" : value;
    }
}
