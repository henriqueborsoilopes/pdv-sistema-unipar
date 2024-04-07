package br.unipar.pdvsistema.tela.tabelaclientes;

import br.unipar.pdvsistema.model.entidade.Cliente;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClienteTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<Cliente> clientes;
    private final String[] colunas = {"Seleção", "Código", "Nome", "Telefone", "CPF"};

    public ClienteTabelaModelo(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 1 -> cliente.getId();
            case 2 -> cliente.getNome();
            case 3 -> cliente.getTelefone();
            case 4 -> cliente.getCpf();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
