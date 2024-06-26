package br.unipar.pdvsistema.tela.tabelaproduto;

import br.unipar.pdvsistema.dto.PaginaDTO;
import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.repositorio.ProdutoRepositorio;
import br.unipar.pdvsistema.model.servico.ProdutoServico;
import br.unipar.pdvsistema.util.FormatarUtil;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProdutoTabelaControlador extends javax.swing.JFrame {
    
    private String pesquisarNome = "";
   
    private PaginaDTO<Produto> pagina = new PaginaDTO<>(0, 10, 0);
    private ProdutoSelecionadoListener produtoSelecionadoListener;
    
    public ProdutoTabelaControlador(Component component) {
        initComponents();
        setLocationRelativeTo(component);
        setVisible(true);
        carregarTabela();
        
        tabelaProdutos.setRowHeight(29);
        txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setText("← " + String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setEnabled(false);
        btProximo.setText(pagina.getPaginaAtual() + " →");
        
        txtPesquisar.requestFocus();
        txtPesquisar.addKeyListener(keyPressed());
        
        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setProdutoSelecionado(tabelaProdutos.rowAtPoint(e.getPoint()));
            }
        });
        
        btAnterior.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaAnterior();
            }
        });
        
        btProximo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                proximaPagina();
          }
        });
        
        btPesquisar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pesquisar();
            }
        });
    }
    
    private KeyAdapter keyPressed() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> pesquisar();
                    case KeyEvent.VK_LEFT-> paginaAnterior();
                    case KeyEvent.VK_RIGHT -> proximaPagina();
                }
            }
        };
    }
    
    public void paginaAnterior() {
        if (btAnterior.isEnabled()) {
            int numPg = pagina.getNumPagina();
            pagina.setNumPagina(numPg -= 1);
            txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
            if (pagina.getNumPagina() >= 1) {
                btAnterior.setText("← " + String.valueOf(pagina.getNumPagina()));
            }
            btProximo.setEnabled(true);
            carregarTabela();
            if (pagina.getNumPagina() < 1) {
                btAnterior.setEnabled(false);
            }
        }
    }
    
    public void proximaPagina() {
        if (btProximo.isEnabled()) {
            btAnterior.setEnabled(true);
            int numPg = pagina.getNumPagina();
            pagina.setNumPagina(numPg += 1);
            txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
            if (pagina.getNumPagina() >= 2) {
                btAnterior.setText("← " + String.valueOf(pagina.getNumPagina()));
            }
            carregarTabela();
            if (pagina.isUltimaPagina()) {
                btProximo.setEnabled(false);
            }
        }
    }
    
    public void pesquisar() {
        pesquisarNome = txtPesquisar.getText();
        pagina.setNumPagina(0);
        carregarTabela();
        txtNumPagina.setText(String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setText("← " + String.valueOf(pagina.getNumPagina() + 1));
        btAnterior.setEnabled(false);
        if (pagina.isUltimaPagina()) {
            btProximo.setEnabled(false);
            btProximo.setText("1 →");
        } else {
            btProximo.setEnabled(true);
            btProximo.setText(String.valueOf(pagina.getPaginaAtual()) + " →");
        }
        txtPesquisar.setText("");
    }
    
    public void addProdutoSelecionadoListener(ProdutoSelecionadoListener produtoSelecionadoListener) {
        this.produtoSelecionadoListener = produtoSelecionadoListener;
    }
    
    private void setProdutoSelecionado(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tabelaProdutos.getRowCount()) {
            String codigo = tabelaProdutos.getValueAt(rowIndex, 0).toString();
            String descricao = tabelaProdutos.getValueAt(rowIndex, 1).toString();
            String valorUnit = tabelaProdutos.getValueAt(rowIndex, 2).toString();
            
            Produto produto = new Produto(Long.valueOf(codigo), descricao, FormatarUtil.valorParaDouble(valorUnit));
            
            if (produtoSelecionadoListener != null) {
                produtoSelecionadoListener.produtoSelecionado(produto);
            }
            
            dispose();
        }
    }
    
    private void carregarTabela() {
        pagina = new ProdutoServico(new ProdutoRepositorio()).acharTodosPaginado(pesquisarNome, pagina.getNumPagina(), pagina.getTamPagina());
        ProdutoTabelaModelo modelo = new ProdutoTabelaModelo(pagina.getConteudo());
        tabelaProdutos.setModel(modelo);
    }
    
    @Override
    public void dispose() {
        super.dispose(); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisar = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btAnterior = new javax.swing.JButton();
        txtNumPagina = new javax.swing.JTextField();
        btProximo = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 500));
        setResizable(false);

        background.setBackground(new java.awt.Color(51, 51, 51));
        background.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtPesquisar.setToolTipText("");
        txtPesquisar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btPesquisar.setBackground(new java.awt.Color(0, 0, 102));
        btPesquisar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btPesquisar.setText("Pesquisar (Enter)");
        btPesquisar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtPesquisar))
                .addContainerGap())
        );

        tabelaProdutos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Código", "Descrição", "valor Unit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaProdutos);
        if (tabelaProdutos.getColumnModel().getColumnCount() > 0) {
            tabelaProdutos.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaProdutos.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelaProdutos.getColumnModel().getColumn(2).setMinWidth(110);
            tabelaProdutos.getColumnModel().getColumn(2).setMaxWidth(110);
        }

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        btAnterior.setBackground(new java.awt.Color(0, 0, 102));
        btAnterior.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAnterior.setForeground(new java.awt.Color(255, 255, 255));
        btAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btAnterior.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtNumPagina.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNumPagina.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumPagina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNumPagina.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumPagina.setEnabled(false);

        btProximo.setBackground(new java.awt.Color(0, 0, 102));
        btProximo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btProximo.setForeground(new java.awt.Color(255, 255, 255));
        btProximo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btProximo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumPagina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btProximo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAnterior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btAnterior;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JToggleButton btProximo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaProdutos;
    private javax.swing.JTextField txtNumPagina;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
