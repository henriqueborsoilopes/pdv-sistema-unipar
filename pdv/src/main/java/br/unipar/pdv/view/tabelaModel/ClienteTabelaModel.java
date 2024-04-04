package br.unipar.pdv.view.tabelaModel;

import br.unipar.pdv.model.entity.Cliente;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClienteTabelaModel extends DefaultTableModel {

    public ClienteTabelaModel() {
        this.addColumn("Id");
        this.addColumn("Nome");
        this.addColumn("Telefone");
        this.addColumn("CPF");
    }
    
    public ClienteTabelaModel(List<Cliente> clientes) {
        this();
        
        for (Cliente cliente : clientes) {
            this.addRow(new String[] {
                cliente.getId().toString(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getCpf()});
        }
    }
    
    public Cliente getSelectedCliente(JTable table, List<Cliente> clientes) {
        return null;
    }
}
