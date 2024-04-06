package br.unipar.pdvsistema.tela.tabelaModelo;

import br.unipar.pdvsistema.model.entidade.Cliente;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClienteTabelaModelo extends DefaultTableModel {

    public ClienteTabelaModelo() {
        this.addColumn("Id");
        this.addColumn("Nome");
        this.addColumn("Telefone");
        this.addColumn("CPF");
    }
    
    public ClienteTabelaModelo(List<Cliente> clientes) {
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
