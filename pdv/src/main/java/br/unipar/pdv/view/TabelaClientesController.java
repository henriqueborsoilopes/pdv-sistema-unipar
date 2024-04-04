package br.unipar.pdv.view;

import br.unipar.pdv.model.entity.Cliente;
import br.unipar.pdv.model.repository.ClienteRepository;
import br.unipar.pdv.model.service.ClienteService;
import br.unipar.pdv.view.tabelaModel.ClienteTabelaModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaClientesController extends javax.swing.JFrame {

    public TabelaClientesController() {
        initComponents();
        setLocationRelativeTo(null);
        carregarTabela();
    }
    
    public void carregarTabela() {
        List<Cliente> clientes = new ArrayList<>();
        
        ClienteService service = new ClienteService(new ClienteRepository());
        
        clientes.addAll(service.acharTodosPaginado("a", 5, 5));
        
        ClienteTabelaModel model = new ClienteTabelaModel(clientes);
        
        //jTableClientes.setModel(model);
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
