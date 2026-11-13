package mx.gob.babien.tcb.util;

import java.util.Locale;
import mx.gob.babien.tcb.dto.request.AltaClienteRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AltaClienteDtoMapper {

    public AltaClienteRequestDto normalize(AltaClienteRequestDto source) {
        AltaClienteRequestDto request = source == null ? new AltaClienteRequestDto() : source;
        AltaClienteRequestDto normalized = new AltaClienteRequestDto();
        normalized.setTpoId(normalize(request.getTpoId(), true));
        normalized.setNumId(normalize(request.getNumId(), true));
        normalized.setNumSuc(normalize(request.getNumSuc(), true));
        normalized.setGenero(normalize(request.getGenero(), true));
        normalized.setNombre(normalize(request.getNombre(), true));
        normalized.setPrimApe(normalize(request.getPrimApe(), true));
        normalized.setSeguApe(normalize(request.getSeguApe(), true));
        normalized.setRegFisc(normalize(request.getRegFisc(), true));
        normalized.setCodEdoCivil(normalize(request.getCodEdoCivil(), true));
        normalized.setCodRegMatrimonio(normalize(request.getCodRegMatrimonio(), true));
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
        normalized.setUsoctaAdmIngreYGasts(normalizeSOrBlank(request.getUsoctaAdmIngreYGasts()));
        normalized.setUsoctaPagoServs(normalizeSOrBlank(request.getUsoctaPagoServs()));
        normalized.setUsoctaDeptoNomin(normalizeSOrBlank(request.getUsoctaDeptoNomin()));
        normalized.setUsoctaOtros(normalizeSOrBlank(request.getUsoctaOtros()));
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
        normalized.setOperaDepositos(normalizeSOrBlank(request.getOperaDepositos()));
        normalized.setOperaRetiros(normalizeSOrBlank(request.getOperaRetiros()));
        normalized.setOperaCobroGiros(normalizeSOrBlank(request.getOperaCobroGiros()));
        normalized.setOperaTransferencias(normalizeSOrBlank(request.getOperaTransferencias()));
        normalized.setDomRegOcupacion(normalize(request.getDomRegOcupacion(), false));
        return normalized;
    }

    public AltaClienteRequestDto toServiceRequest(AltaClienteRequestDto request) {
        AltaClienteRequestDto normalized = request == null ? new AltaClienteRequestDto() : request;
        AltaClienteRequestDto serviceRequest = new AltaClienteRequestDto();
        serviceRequest.setTpoId(emptyIfNull(normalized.getTpoId()));
        serviceRequest.setNumId(emptyIfNull(normalized.getNumId()));
        serviceRequest.setNumSuc(emptyIfNull(normalized.getNumSuc()));
        serviceRequest.setGenero(emptyIfNull(normalized.getGenero()));
        serviceRequest.setNombre(emptyIfNull(normalized.getNombre()));
        serviceRequest.setPrimApe(emptyIfNull(normalized.getPrimApe()));
        serviceRequest.setSeguApe(emptyIfNull(normalized.getSeguApe()));
        serviceRequest.setRegFisc(emptyIfNull(normalized.getRegFisc()));
        serviceRequest.setCodEdoCivil(emptyIfNull(normalized.getCodEdoCivil()));
        serviceRequest.setCodRegMatrimonio(emptyIfNull(normalized.getCodRegMatrimonio()));
        serviceRequest.setRfc(emptyIfNull(normalized.getRfc()));
        serviceRequest.setCurp(emptyIfNull(normalized.getCurp()));
        serviceRequest.setCodPaisNal(emptyIfNull(normalized.getCodPaisNal()));
        serviceRequest.setCodPaisRes(emptyIfNull(normalized.getCodPaisRes()));
        serviceRequest.setCodCno(emptyIfNull(normalized.getCodCno()));
        serviceRequest.setCodCnae(emptyIfNull(normalized.getCodCnae()));
        serviceRequest.setFecNacto(emptyIfNull(normalized.getFecNacto()));
        serviceRequest.setCodPostalLugNacto(emptyIfNull(normalized.getCodPostalLugNacto()));
        serviceRequest.setCodEdoNac(emptyIfNull(normalized.getCodEdoNac()));
        serviceRequest.setCodPaisNacto(emptyIfNull(normalized.getCodPaisNacto()));
        serviceRequest.setTransNumDepo(emptyIfNull(normalized.getTransNumDepo()));
        serviceRequest.setTransImptDepo(emptyIfNull(normalized.getTransImptDepo()));
        serviceRequest.setTransNumReti(emptyIfNull(normalized.getTransNumReti()));
        serviceRequest.setTransImptReti(emptyIfNull(normalized.getTransImptReti()));
        serviceRequest.setFteingrHonorarios(emptyIfNull(normalized.getFteingrHonorarios()));
        serviceRequest.setFteingrSueldos(emptyIfNull(normalized.getFteingrSueldos()));
        serviceRequest.setFteingrArrendamto(emptyIfNull(normalized.getFteingrArrendamto()));
        serviceRequest.setFteingrActvComercial(emptyIfNull(normalized.getFteingrActvComercial()));
        serviceRequest.setFteingrOtros(emptyIfNull(normalized.getFteingrOtros()));
        serviceRequest.setFteingrDescribaOtros(emptyIfNull(normalized.getFteingrDescribaOtros()));
        serviceRequest.setFteingrMtoMenIngresos(emptyIfNull(normalized.getFteingrMtoMenIngresos()));
        serviceRequest.setUsoctaAdmIngreYGasts(emptyIfNull(normalized.getUsoctaAdmIngreYGasts()));
        serviceRequest.setUsoctaPagoServs(emptyIfNull(normalized.getUsoctaPagoServs()));
        serviceRequest.setUsoctaDeptoNomin(emptyIfNull(normalized.getUsoctaDeptoNomin()));
        serviceRequest.setUsoctaOtros(emptyIfNull(normalized.getUsoctaOtros()));
        serviceRequest.setUsoctaDescribaOtros(emptyIfNull(normalized.getUsoctaDescribaOtros()));
        serviceRequest.setOpereaPorcPropios(emptyIfNull(normalized.getOpereaPorcPropios()));
        serviceRequest.setOpereaPorcTerceros(emptyIfNull(normalized.getOpereaPorcTerceros()));
        serviceRequest.setPersonaPoliExpuesta(emptyIfNull(normalized.getPersonaPoliExpuesta()));
        serviceRequest.setPersonaPoliExpuestaDesc(emptyIfNull(normalized.getPersonaPoliExpuestaDesc()));
        serviceRequest.setRelPersonaPoliExpuest(emptyIfNull(normalized.getRelPersonaPoliExpuest()));
        serviceRequest.setRelPersonaPoliExpuestDesc(emptyIfNull(normalized.getRelPersonaPoliExpuestDesc()));
        serviceRequest.setRelPersonaPoliExpuestNom(emptyIfNull(normalized.getRelPersonaPoliExpuestNom()));
        serviceRequest.setDomNumTelefDom(emptyIfNull(normalized.getDomNumTelefDom()));
        serviceRequest.setDomTipoCalle(emptyIfNull(normalized.getDomTipoCalle()));
        serviceRequest.setDomNombCalle(emptyIfNull(normalized.getDomNombCalle()));
        serviceRequest.setDomNumExt(emptyIfNull(normalized.getDomNumExt()));
        serviceRequest.setDomNumInt(emptyIfNull(normalized.getDomNumInt()));
        serviceRequest.setDomNombColonia(emptyIfNull(normalized.getDomNombColonia()));
        serviceRequest.setDomCodPostal(emptyIfNull(normalized.getDomCodPostal()));
        serviceRequest.setNombCorreo(emptyIfNull(normalized.getNombCorreo()));
        serviceRequest.setCodDoc1(emptyIfNull(normalized.getCodDoc1()));
        serviceRequest.setDescCod1(emptyIfNull(normalized.getDescCod1()));
        serviceRequest.setCodDoc2(emptyIfNull(normalized.getCodDoc2()));
        serviceRequest.setDescCod2(emptyIfNull(normalized.getDescCod2()));
        serviceRequest.setCodDoc3(emptyIfNull(normalized.getCodDoc3()));
        serviceRequest.setDescCod3(emptyIfNull(normalized.getDescCod3()));
        serviceRequest.setCodDoc4(emptyIfNull(normalized.getCodDoc4()));
        serviceRequest.setDescCod4(emptyIfNull(normalized.getDescCod4()));
        serviceRequest.setOperaDepositos(emptyIfNull(normalized.getOperaDepositos()));
        serviceRequest.setOperaRetiros(emptyIfNull(normalized.getOperaRetiros()));
        serviceRequest.setOperaCobroGiros(emptyIfNull(normalized.getOperaCobroGiros()));
        serviceRequest.setOperaTransferencias(emptyIfNull(normalized.getOperaTransferencias()));
        serviceRequest.setDomRegOcupacion(emptyIfNull(normalized.getDomRegOcupacion()));
        return serviceRequest;
    }

    private static String normalize(String value, boolean uppercase) {
        if (value == null) {
            return "";
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return "";
        }
        return uppercase ? normalized.toUpperCase(Locale.ROOT) : normalized;
    }

    private static String normalizeDocumentValue(String value, boolean uppercase) {
        String normalized = normalize(value, uppercase);
        return normalized.isEmpty() ? " " : normalized;
    }

    private static String normalizeSOrBlank(String value) {
        String normalized = normalize(value, true);
        return "S".equals(normalized) ? "S" : " ";
    }

    private static String emptyIfNull(String value) {
        return value == null ? "" : value;
    }
}
