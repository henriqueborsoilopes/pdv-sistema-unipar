package br.unipar.pdvsistema.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatarUtil {
    
    public static int arredondaParaCima(double valor) {
        return (int) Math.ceil(valor);
    }
    
    public static String doubleParaReal(Double valor) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,#00.00", simbolos);
        return formato.format(valor);
    }
    
    public static Double realParaDouble(String valor) {
        valor = valor.replaceAll("\\.", "");
        valor = valor.replace(',', '.');
        return Double.valueOf(valor);
    }
}
