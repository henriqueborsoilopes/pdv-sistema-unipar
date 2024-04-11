package br.unipar.pdvsistema.tela;

import br.unipar.pdvsistema.model.entidade.ItemVenda;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MainItemVendaTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<ItemVenda> itens;
    private final String[] colunas = {"Código", "Descrição", "Valor Unit", "Qtd", "Desc", "Valor Total"};

    public MainItemVendaTabelaModelo(List<ItemVenda> itens) {
        this.itens = itens;
    }

    @Override
    public int getRowCount() {
        return itens.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemVenda item = itens.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getProduto().getId();
            case 1 -> item.getDescricao();
            case 2 -> item.getValorUnit();
            case 3 -> item.getQuantidade();
            case 4 -> item.getDesconto();
            case 5 -> item.getValorTotalItem();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}