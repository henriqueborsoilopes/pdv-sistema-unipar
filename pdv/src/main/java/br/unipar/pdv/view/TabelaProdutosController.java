package br.unipar.pdv.view;

import br.unipar.pdv.model.entity.Produto;
import br.unipar.pdv.model.repository.ProdutoRepository;
import br.unipar.pdv.model.service.ProdutoService;
import br.unipar.pdv.view.tabelaModel.ProdutoTabelaModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaProdutosController extends javax.swing.JFrame {

    public TabelaProdutosController() {
        initComponents();
        setLocationRelativeTo(null);
        carregarTabela();
    }
    
    public void carregarTabela() {
        List<Produto> produtos = new ArrayList<>();
        
        ProdutoService service = new ProdutoService(new ProdutoRepository());
        
        produtos.addAll(service.acharTodosPaginado("a", 5, 5));
        
        ProdutoTabelaModel model = new ProdutoTabelaModel(produtos);
        
        //jTableProdutos.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
