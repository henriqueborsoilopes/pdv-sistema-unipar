package br.unipar.pdvsistema.tela.relatorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioControlador {
    
    public static void carregaRelatori(Long id_venda) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pdvsistema", "postgres", "12345678")) {
            // Carregar o arquivo Jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\Projetos\\pdv-sistema-unipar\\pdvsistema\\src\\main\\java\\br\\unipar\\pdvsistema\\tela\\relatorio\\comprovante_venda.jasper");

            // Preencher os parâmetros, se houver
            Map<String, Object> parametros = new HashMap<>();
            // Exemplo de parâmetro:
             parametros.put("ID_VENDA", id_venda);

            // Preencher os dados (se necessário), neste exemplo, passamos um objeto vazio.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);

            // Exibir o relatório em um visualizador
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

            // Exportar para PDF
//            String outputFile = "C:\\Projetos\\pdv-sistema-unipar\\pdvsistema\\src\\main\\java\\br\\unipar\\pdvsistema\\tela\\relatorio\\";
//            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
//            System.out.println("PDF gerado em: " + outputFile);
        } catch (JRException | SQLException e) {
        }
    }
}
