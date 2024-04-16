package br.unipar.pdvsistema.tela;

import br.unipar.pdvsistema.model.entidade.Pagamento;
import br.unipar.pdvsistema.util.FormatarUtil;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MainPagamentoTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<Pagamento> pagamentos;
    private final String[] colunas = {"Forma", "Parcela", "Valor Total"};

    public MainPagamentoTabelaModelo(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Override
    public int getRowCount() {
        return pagamentos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pagamento pagamento = pagamentos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> pagamento.getTipoPagamento().getDescricao();
            case 1 -> pagamento.getParcelas();
            case 2 -> FormatarUtil.valorParaBR(pagamento.getValorPago());
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
