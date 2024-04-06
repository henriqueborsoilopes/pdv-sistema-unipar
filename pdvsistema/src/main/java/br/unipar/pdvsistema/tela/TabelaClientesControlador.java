package br.unipar.pdvsistema.tela;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.repositorio.ClienteRepositorio;
import br.unipar.pdvsistema.model.servico.ClienteServico;
import br.unipar.pdvsistema.tela.tabelaModelo.ClienteTabelaModelo;
import java.util.ArrayList;
import java.util.List;

public class TabelaClientesControlador extends javax.swing.JFrame {

    public TabelaClientesControlador() {
        initComponents();
        setLocationRelativeTo(null);
        carregarTabela();
    }
    
    public void carregarTabela() {
        List<Cliente> clientes = new ArrayList<>();
        
        ClienteServico service = new ClienteServico(new ClienteRepositorio());
        
        clientes.addAll(service.acharTodosPaginado("a", 5, 5));
        
        ClienteTabelaModelo model = new ClienteTabelaModelo(clientes);
        
        //jTableClientes.setModel(model);
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
