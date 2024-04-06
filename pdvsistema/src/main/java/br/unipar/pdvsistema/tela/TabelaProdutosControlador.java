package br.unipar.pdvsistema.tela;

import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.repositorio.ProdutoRepositorio;
import br.unipar.pdvsistema.model.servico.ProdutoServico;
import br.unipar.pdvsistema.tela.tabelaModelo.ProdutoTabelaModelo;
import java.util.ArrayList;
import java.util.List;

public class TabelaProdutosControlador extends javax.swing.JFrame {

    public TabelaProdutosControlador() {
        initComponents();
        setLocationRelativeTo(null);
        carregarTabela();
    }
    
    public void carregarTabela() {
        List<Produto> produtos = new ArrayList<>();
        
        ProdutoServico service = new ProdutoServico(new ProdutoRepositorio());
        
        produtos.addAll(service.acharTodosPaginado("a", 5, 5));
        
        ProdutoTabelaModelo model = new ProdutoTabelaModelo(produtos);
        
        //jTableProdutos.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
