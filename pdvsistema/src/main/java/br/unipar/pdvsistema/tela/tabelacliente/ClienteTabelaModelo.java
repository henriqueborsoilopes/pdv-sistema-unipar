package br.unipar.pdvsistema.tela.tabelacliente;

import br.unipar.pdvsistema.model.entidade.Cliente;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClienteTabelaModelo extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private final List<Cliente> clientes;
    private final String[] colunas = {"Código", "Nome", "Telefone", "CPF"};

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
            case 0 -> cliente.getId();
            case 1 -> cliente.getNome();
            case 2 -> cliente.getTelefone();
            case 3 -> cliente.getCpf();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
