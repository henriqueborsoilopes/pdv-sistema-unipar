package br.unipar.pdvsistema.util;

import java.text.DecimalFormat;

public class FormatarUtil {
    
    public static int arredondaParaCima(double valor) {
        return (int) Math.ceil(valor);
    }
    
    public static String formataCasaDecimal(double valor) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(valor);
}
    
//    public static String doubleParaReal(Double valor) {
//        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
//        simbolos.setDecimalSeparator(',');
//        simbolos.setGroupingSeparator('.');
//        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
//        return formato.format(valor);
//    }
//    
//    public static Double realParaDouble(String valor) {
//        valor = valor.replaceAll("\\.", "");
//        valor = valor.replace(',', '.');
//        return Double.valueOf(valor);
//    }
}
