package mx.gob.babien.tcb.catalog;

import java.util.List;

public final class EconomicActivityCnaeCatalog {

    // CNAE activity catalog: UI shows description, submit sends the selected code only.
    private static final List<EconomicActivityCnaeOption> ACTIVITIES = List.of(
            new EconomicActivityCnaeOption("6228010", "DISTRIBUCION Y COMPRAVENTA DE PERIODICOS Y REVISTA"),
            new EconomicActivityCnaeOption("2912013", "EDICION DE LIBROS"),
            new EconomicActivityCnaeOption("2912005", "EDICION DE LIBROS Y SIMILARES"),
            new EconomicActivityCnaeOption("2911015", "EDICION DE PERIODICOS Y REVISTAS"),
            new EconomicActivityCnaeOption("3319010", "ELABORACION DE OBJETOS ARTISTICOS DE ALFARERIA Y C"),
            new EconomicActivityCnaeOption("2115013", "ELABORACION DE PULQUE"),
            new EconomicActivityCnaeOption("2131019", "EMBOTELLADO DE AGUAS MINERALES"),
            new EconomicActivityCnaeOption("2049014", "EMPACADORA DE CARNE"),
            new EconomicActivityCnaeOption("2012011", "EMPACADORA DE CONSERVAS ALIMENTICIAS"),
            new EconomicActivityCnaeOption("2099019", "EMPACADORA DE ESPECIAS"),
            new EconomicActivityCnaeOption("2012029", "EMPACADORA DE FRUTAS Y LEGUMBRES"),
            new EconomicActivityCnaeOption("2061026", "EMPACADORA DE OTROS MARISCOS"),
            new EconomicActivityCnaeOption("2061034", "EMPACADORA DE PESCADO"),
            new EconomicActivityCnaeOption("2027010", "EMPACADORA DE TE"),
            new EconomicActivityCnaeOption("8944098", "EMPLEADO PRIVADO"),
            new EconomicActivityCnaeOption("8935019", "EMPRESA DE SERVICIO DE LIMPIEZA"),
            new EconomicActivityCnaeOption("7611016", "EMPRESA DE TELEFONOS"),
            new EconomicActivityCnaeOption("7612014", "EMPRESA DE TELEGRAFOS"),
            new EconomicActivityCnaeOption("8219083", "EMPRESAS CONTROLADORAS FINANCIERAS"),
            new EconomicActivityCnaeOption("8429020", "EMPRESAS CONTROLADORAS NO FINANCIERAS"),
            new EconomicActivityCnaeOption("8219122", "EMPRESAS DE AUTOFINANCIAMIENTO AUTOMOTRIZ"),
            new EconomicActivityCnaeOption("8219130", "EMPRESAS DE AUTOFINANCIAMIENTO RESIDENCIAL"),
            new EconomicActivityCnaeOption("8429038", "EMPRESAS DE SEGURIDAD PRIVADA"),
            new EconomicActivityCnaeOption("8429046", "EMPRESAS TRANSPORTADORAS DE VALORES"),
            new EconomicActivityCnaeOption("2921014", "ENCUADERNACION"),
            new EconomicActivityCnaeOption("9119018", "ESTABLECIMIENTOS PRIVADOS DE INSTRUCCION EDUCACION"),
            new EconomicActivityCnaeOption("9115016", "ESTABLECIMIENTOS PUBLICOS DE INSTRUCCION EDUCACION"),
            new EconomicActivityCnaeOption("8916019", "ESTACIONAMIENTO PRIVADO PARA VEHICULOS"),
            new EconomicActivityCnaeOption("8916027", "ESTACIONAMIENTO PUBLICO PARA VEHICULOS"),
            new EconomicActivityCnaeOption("8829014", "ESTADIOS Y ARENAS"),
            new EconomicActivityCnaeOption("8941016", "ESTUDIO FOTOGRAFICO"),
            new EconomicActivityCnaeOption("8821010", "ESTUDIOS CINEMATOGRAFICOS"),
            new EconomicActivityCnaeOption("1211010", "EXPLORACION DE PETROLEO POR COMPAQIAS"),
            new EconomicActivityCnaeOption("0329010", "EXPLOTACION DE BARBASCO,ARBORESCENTES Y ARBUSTOS"),
            new EconomicActivityCnaeOption("0322016", "EXPLOTACION DE CANDELILLA"),
            new EconomicActivityCnaeOption("0322024", "EXPLOTACION DE HULE"),
            new EconomicActivityCnaeOption("0323014", "EXPLOTACION DE RAICES"),
            new EconomicActivityCnaeOption("1511014", "EXPLOTACION DE SAL MARINA Y DE YACIMIENTOS"),
            new EconomicActivityCnaeOption("0323022", "EXTRACCION DE ALQUITRAN VEGETAL"),
            new EconomicActivityCnaeOption("0313015", "EXTRACCION DE CARBON VEGETAL"),
            new EconomicActivityCnaeOption("0321018", "EXTRACCION DE CHICLE"),
            new EconomicActivityCnaeOption("0322032", "EXTRACCION DE COLONIA"),
            new EconomicActivityCnaeOption("0322040", "EXTRACCION DE OTRAS RESINAS"),
            new EconomicActivityCnaeOption("1211028", "EXTRACCION DE PETROLEO CRUDO Y GAS NATURAL"),
            new EconomicActivityCnaeOption("0323030", "EXTRACCION DE TREMENTINA"),
            new EconomicActivityCnaeOption("0312017", "EXTRACCION DE TRONCOS PARA ASERRADEROS Y PARA PULP"),
            new EconomicActivityCnaeOption("1412014", "EXTRACCION DE YESO"),
            new EconomicActivityCnaeOption("1421015", "EXTRACCION Y BENEFICIO DE ARCILLAS REFRACTARIAS"),
            new EconomicActivityCnaeOption("1413012", "EXTRACCION Y BENEFICIO DE ARENA Y GRAVA"),
            new EconomicActivityCnaeOption("1431014", "EXTRACCION Y BENEFICIO DE BARITA Y ROCA FOSFORICA"),
            new EconomicActivityCnaeOption("1111012", "EXTRACCION Y BENEFICIO DE CARBON MINERAL Y GRAFITO"),
            new EconomicActivityCnaeOption("1329011", "EXTRACCION Y BENEFICIO DE COBRE, PLOMO, ZINC Y OT"),
            new EconomicActivityCnaeOption("1432012", "EXTRACCION Y BENEFICIO DE FLUORITA"),
            new EconomicActivityCnaeOption("1322015", "EXTRACCION Y BENEFICIO DE MERCURIO Y ANTIMONIO"),
            new EconomicActivityCnaeOption("1311018", "EXTRACCION Y BENEFICIO DE MINERAL DE HIERRO"),
            new EconomicActivityCnaeOption("1321017", "EXTRACCION Y BENEFICIO DE ORO PLATA Y OTROS METALE"),
            new EconomicActivityCnaeOption("1419010", "EXTRACCION Y BENEFICIO DE OTROS MATERIALES PARA CO"),
            new EconomicActivityCnaeOption("1439018", "EXTRACCION Y BENEFICIO DE OTROS MINERALES NO METAL"),
            new EconomicActivityCnaeOption("1411016", "EXTRACCION Y BENEFICIO DE PIEDRA"),
            new EconomicActivityCnaeOption("1433010", "EXTRACCION Y BENEFICIO DE SILICE"),
            new EconomicActivityCnaeOption("3999010", "FABRICACION DE ABANICOS"),
            new EconomicActivityCnaeOption("3021011", "FABRICACION DE ABONOS Y FERTILIZANTES QUIMICOS"),
            new EconomicActivityCnaeOption("3352010", "FABRICACION DE ABRASIVOS"),
            new EconomicActivityCnaeOption("3699032", "FABRICACION DE ACCESORIOS PARA LA INDUSTRIA TEXTIL"),
            new EconomicActivityCnaeOption("2091015", "FABRICACION DE ACEITES VEGETALES COMESTIBLES"),
            new EconomicActivityCnaeOption("3113016", "FABRICACION DE ACEITES Y LUBRICANTES"),
            new EconomicActivityCnaeOption("2099027", "FABRICACION DE ACEITES Y MANTECAS ANIMALES COMESTI"),
            new EconomicActivityCnaeOption("3013018", "FABRICACION DE ACIDOS INDUSTRIALES"),
            new EconomicActivityCnaeOption("3791010", "FABRICACION DE ACUMULADORES"),
            new EconomicActivityCnaeOption("3331014", "FABRICACION DE ADOBE"),
            new EconomicActivityCnaeOption("2112019", "FABRICACION DE AGUARDIENTE DE CAPA"),
            new EconomicActivityCnaeOption("3093010", "FABRICACION DE AGUARRAS"),
            new EconomicActivityCnaeOption("3593010", "FABRICACION DE ALAMBRADOS Y TELAS DE METAL"),
            new EconomicActivityCnaeOption("3593028", "FABRICACION DE ALAMBRE"),
            new EconomicActivityCnaeOption("3599018", "FABRICACION DE ALFILERES AGUJAS Y BROCHES"),
            new EconomicActivityCnaeOption("2433019", "FABRICACION DE ALGODON ABSORBENTE VENDAS TELA ADEH"),
            new EconomicActivityCnaeOption("3051018", "FABRICACION DE ALGODON ESTERILIZADO GASAS Y VEND"),
            new EconomicActivityCnaeOption("2098029", "FABRICACION DE ALIMENTO PARA GANADO Y OTROS ANIMAL"),
            new EconomicActivityCnaeOption("2098037", "FABRICACION DE ALIMENTOS PARA AVES"),
            new EconomicActivityCnaeOption("2092013", "FABRICACION DE ALMIDON"),
            new EconomicActivityCnaeOption("2713015", "FABRICACION DE ALMOHADAS Y COJINES")
    );

    private EconomicActivityCnaeCatalog() {
    }

    public static List<EconomicActivityCnaeOption> activities() {
        return ACTIVITIES;
    }

    public record EconomicActivityCnaeOption(String code, String description) {
    }
}
