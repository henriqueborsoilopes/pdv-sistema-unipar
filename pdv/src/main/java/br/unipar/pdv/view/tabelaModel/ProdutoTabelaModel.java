package br.unipar.pdv.view.tabelaModel;

import br.unipar.pdv.model.entity.Cliente;
import br.unipar.pdv.model.entity.Produto;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProdutoTabelaModel extends DefaultTableModel {

    public ProdutoTabelaModel() {
        this.addColumn("Id");
        this.addColumn("Descrição");
    }
    
    public ProdutoTabelaModel(List<Produto> produtos) {
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
