package br.unipar.pdvsistema.tela.tabelaModelo;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.entidade.Produto;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ItemProdutoTabelaModelo extends DefaultTableModel {

    public ItemProdutoTabelaModelo() {
        this.addColumn("Id");
        this.addColumn("Descrição");
    }
    
    public ItemProdutoTabelaModelo(List<Produto> produtos) {
        this();
        
        for (Produto produto : produtos) {
            this.addRow(new String[] {
                produto.getId().toString(),
                produto.getDescricao()});
        }
    }
    
    public Cliente getSelectedCliente(JTable table, List<Cliente> clientes) {
        return null;
    }
}
