package br.unipar.pdvsistema.tela.tabelaproduto;

import br.unipar.pdvsistema.model.entidade.Produto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProdutoTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<Produto> produtos;
    private final String[] colunas = {"Código", "Descrição", "Valor Unit"};

    public ProdutoTabelaModelo(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = produtos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> produto.getId();
            case 1 -> produto.getDescricao();
            case 2 -> produto.getValorUnit();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
