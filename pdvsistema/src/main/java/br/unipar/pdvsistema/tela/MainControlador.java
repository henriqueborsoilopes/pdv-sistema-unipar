package br.unipar.pdvsistema.tela;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.entidade.ItemVenda;
import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.repositorio.VendaRepositorio;
import br.unipar.pdvsistema.model.servico.VendaServico;
import br.unipar.pdvsistema.model.servico.excecao.BancoDadosExcecao;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;
import br.unipar.pdvsistema.tela.relatorio.RelatorioControlador;
import br.unipar.pdvsistema.tela.tabelaclientes.ClienteTabelaControlador;
import br.unipar.pdvsistema.tela.tabelaprodutos.ProdutoTabelaControlador;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainControlador extends JFrame {
    
    private Integer qtdProduto = 1;
    private final Double descontoProduto = 0.0;
    private final Double descontoVenda = 0.00;
    
    private Produto produto;
    private Cliente cliente;
    private Venda venda;
    
    public MainControlador() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        novaVenda();
        atulizarColunasLinhasTabelaItem();
        atulizarColunasLinhasTabelaPagamento();
        
        Background.requestFocus();
        Background.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                Background.requestFocusInWindow();
            }
        });
        Background.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 38 -> aumentarQtd();
                    case 40 -> diminuirQtd();
                    case KeyEvent.VK_F2 -> abrirClienteTabelaControlador();
                    case KeyEvent.VK_F1 -> abrirProdutoTabelaControlador();
                    case KeyEvent.VK_ENTER -> addItemVenda();
                    case KeyEvent.VK_F9 -> novaVenda();
                    case KeyEvent.VK_F5 -> salvarVenda();
                }
            }
        });
        
        btAdicionarProduto.addActionListener((ActionEvent e) -> {
            addItemVenda();
        });
        
        btNovoCliente.addActionListener((ActionEvent e) -> {
            abrirClienteTabelaControlador();
        });
        
        btNovoProduto.addActionListener((ActionEvent e) -> {
            abrirProdutoTabelaControlador();
        });
        
        btNovaVenda.addActionListener((ActionEvent e) -> {
            novaVenda();
        });
        
        btSalvarVenda.addActionListener((ActionEvent e) -> {
            salvarVenda();
        });
    }
    
    private void salvarVenda() {
        VendaServico servico = new VendaServico(new VendaRepositorio());
        try {
            Object[] options = {"Imprimir", "Não"};
            
            venda = servico.inserir(venda);
            int n = JOptionPane.showOptionDialog(null, 
                    "Gostaria de imprimir o comprovante?", 
                    "Comprovante", JOptionPane.YES_NO_CANCEL_OPTION,  
                    JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == 0) {
                RelatorioControlador.carregaRelatori(venda.getId());
            }
            novaVenda();
        } catch (BancoDadosExcecao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (ValidacaoExcecao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
    private void atulizarColunasLinhasTabelaItem() {
        tabelaItens.setRowHeight(30);
    }
    
    private void atulizarColunasLinhasTabelaPagamento() {
        tabelaPagamentos.setRowHeight(30);
    }
    
    private void limparCamposProduto() {
        txtCodigoProduto.setText("");
        txtDescricaoProduto.setText("");
        qtdProduto = 1;
        txtQtd.setText(String.valueOf(qtdProduto));
    }
    
    private void limparCamposCliente() {
        txtCodigoCliente.setText("");
        txtNomeCliente.setText("");
    }
        
    private void exibirVenda() {
        txtValorTotalVenda.setText(venda.getValorTotal().toString());
        txtValorDescontoVenda.setText(venda.getDesconto().toString());
    }
    
    private void atualizarVenda() {
        venda.setCliente(cliente);
        venda.setDesconto(descontoVenda);
        exibirVenda();
    }
    
    private void novaVenda() {
        venda = new Venda(null, descontoVenda, null);
        exibirVenda();
        atualizarItemVendaTabela();
        limparCamposCliente();
    }
    
    private void addItemVenda() {
        if (produto != null) {
            venda.addItem(new ItemVenda(venda, produto, produto.getDescricao(), qtdProduto, produto.getValorUnit(), descontoProduto));
            atualizarItemVendaTabela();
            limparCamposProduto();
            produto = null;
        }
    }
    
    private void atualizarItemVendaTabela() {
        MainItemVendaTabelaModelo modelo = new MainItemVendaTabelaModelo(venda.getItens());
        tabelaItens.setModel(modelo);
        exibirVenda();
    }
    
    private void abrirClienteTabelaControlador() {
        ClienteTabelaControlador controlador = new ClienteTabelaControlador(this);
        controlador.addClienteSelecionadoListener((Cliente cliente1) -> {
            exibirCliente(cliente1);
            synchronized (MainControlador.this) {
                MainControlador.this.notify();
            }
        });
    }
    
    private void abrirProdutoTabelaControlador() {
        ProdutoTabelaControlador controlador = new ProdutoTabelaControlador(this);
        controlador.addProdutoSelecionadoListener((Produto produto1) -> {
            exibirProduto(produto1);
            synchronized (MainControlador.this) {
                MainControlador.this.notify();
            }
        });
    }
    
    private void exibirProduto(Produto produto) {
        this.produto = produto;
        txtCodigoProduto.setText(produto.getId().toString());
        txtDescricaoProduto.setText(produto.getDescricao());
    }
    
    private void exibirCliente(Cliente cliente) {
        this.cliente = cliente;
        txtCodigoCliente.setText(cliente.getId().toString());
        txtNomeCliente.setText(cliente.getNome());
        atualizarVenda();
    }
    
    private void aumentarQtd() {
        qtdProduto += 1;
        txtQtd.setText(String.valueOf(qtdProduto));
    }
    
    private void diminuirQtd() {
        if (qtdProduto > 1) {
            qtdProduto -= 1;
            txtQtd.setText(String.valueOf(qtdProduto));
        }
    }
    
    @Override
    public void dispose() {
        super.dispose(); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jMenuItem1 = new javax.swing.JMenuItem();
        Background = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtDescricaoProduto = new javax.swing.JTextField();
        txtCodigoProduto = new javax.swing.JTextField();
        btAdicionarProduto = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btAumentarQtd = new javax.swing.JButton();
        btDiminuirQtd = new javax.swing.JButton();
        txtQtd = new javax.swing.JTextField();
        btNovoProduto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btNovoCliente = new javax.swing.JButton();
        txtNomeCliente = new javax.swing.JTextField();
        txtCodigoCliente = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaPagamentos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        btSalvarVenda = new javax.swing.JButton();
        btNovaVenda = new javax.swing.JButton();
        txtValorTotalVenda = new javax.swing.JTextField();
        txtValorDescontoVenda = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descrição", "QTD", "Valor Unit", "Desconto", "Total", "Editar", "Exclir"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMinWidth(60);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(3).setMinWidth(70);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(4).setMinWidth(70);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(5).setMinWidth(70);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(6).setMinWidth(50);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(7).setMinWidth(50);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
        }

        jTextField3.setText("00,00");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDV");
        setBackground(new java.awt.Color(0, 102, 102));
        setName("main"); // NOI18N
        setSize(new java.awt.Dimension(1200, 800));

        Background.setBackground(new java.awt.Color(51, 51, 51));
        Background.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtDescricaoProduto.setToolTipText("");
        txtDescricaoProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtDescricaoProduto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescricaoProduto.setEnabled(false);

        txtCodigoProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCodigoProduto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigoProduto.setEnabled(false);

        btAdicionarProduto.setBackground(new java.awt.Color(0, 102, 0));
        btAdicionarProduto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAdicionarProduto.setForeground(new java.awt.Color(255, 255, 255));
        btAdicionarProduto.setText("Adicionar (Enter)");
        btAdicionarProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Desconto");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0.00");
        jTextField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btAumentarQtd.setBackground(new java.awt.Color(0, 0, 102));
        btAumentarQtd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAumentarQtd.setForeground(new java.awt.Color(255, 255, 255));
        btAumentarQtd.setText("+ (↑)");
        btAumentarQtd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btAumentarQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAumentarQtdActionPerformed(evt);
            }
        });

        btDiminuirQtd.setBackground(new java.awt.Color(153, 0, 0));
        btDiminuirQtd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDiminuirQtd.setForeground(new java.awt.Color(255, 255, 255));
        btDiminuirQtd.setText("-  (↓)");
        btDiminuirQtd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btDiminuirQtd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDiminuirQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDiminuirQtdActionPerformed(evt);
            }
        });

        txtQtd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtQtd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQtd.setText("1");
        txtQtd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtQtd.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        btNovoProduto.setBackground(new java.awt.Color(0, 0, 102));
        btNovoProduto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btNovoProduto.setForeground(new java.awt.Color(255, 255, 255));
        btNovoProduto.setText("+ Novo produto (F1)");
        btNovoProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btNovoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricaoProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btDiminuirQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAumentarQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAdicionarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAdicionarProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoProduto)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addComponent(btAumentarQtd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(btDiminuirQtd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtQtd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDescricaoProduto)
                    .addComponent(btNovoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabelaItens.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelaItens.setForeground(new java.awt.Color(102, 102, 102));
        tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "Valor Unit", "Qtd", "Desc", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaItens.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(tabelaItens);
        tabelaItens.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tabelaItens.getColumnModel().getColumnCount() > 0) {
            tabelaItens.getColumnModel().getColumn(0).setMinWidth(60);
            tabelaItens.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaItens.getColumnModel().getColumn(2).setMinWidth(75);
            tabelaItens.getColumnModel().getColumn(2).setMaxWidth(75);
            tabelaItens.getColumnModel().getColumn(3).setMinWidth(60);
            tabelaItens.getColumnModel().getColumn(3).setMaxWidth(60);
            tabelaItens.getColumnModel().getColumn(4).setMinWidth(75);
            tabelaItens.getColumnModel().getColumn(4).setMaxWidth(75);
            tabelaItens.getColumnModel().getColumn(5).setMinWidth(75);
            tabelaItens.getColumnModel().getColumn(5).setMaxWidth(75);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btNovoCliente.setBackground(new java.awt.Color(0, 0, 102));
        btNovoCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btNovoCliente.setForeground(new java.awt.Color(255, 255, 255));
        btNovoCliente.setText("+ Novo cliente (F2)");
        btNovoCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btNovoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoClienteActionPerformed(evt);
            }
        });
        btNovoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btNovoClienteKeyPressed(evt);
            }
        });

        txtNomeCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNomeCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNomeCliente.setEnabled(false);

        txtCodigoCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCodigoCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigoCliente.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btNovoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeCliente)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btNovoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtCodigoCliente))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton2.setBackground(new java.awt.Color(0, 0, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("+ Novo pagamento (F3)");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabelaPagamentos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelaPagamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Qtd", "Forma", "Parcela", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaPagamentos);
        if (tabelaPagamentos.getColumnModel().getColumnCount() > 0) {
            tabelaPagamentos.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaPagamentos.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelaPagamentos.getColumnModel().getColumn(2).setMinWidth(50);
            tabelaPagamentos.getColumnModel().getColumn(2).setMaxWidth(50);
            tabelaPagamentos.getColumnModel().getColumn(3).setMinWidth(75);
            tabelaPagamentos.getColumnModel().getColumn(3).setMaxWidth(75);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton6.setBackground(new java.awt.Color(204, 102, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Aguardar (F7)");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btSalvarVenda.setBackground(new java.awt.Color(0, 102, 0));
        btSalvarVenda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSalvarVenda.setForeground(new java.awt.Color(255, 255, 255));
        btSalvarVenda.setText("Finalizar venda (F5)");
        btSalvarVenda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btNovaVenda.setBackground(new java.awt.Color(0, 0, 102));
        btNovaVenda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btNovaVenda.setForeground(new java.awt.Color(255, 255, 255));
        btNovaVenda.setText("+ Nova venda (F9)");
        btNovaVenda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtValorTotalVenda.setEditable(false);
        txtValorTotalVenda.setBackground(new java.awt.Color(255, 255, 255));
        txtValorTotalVenda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtValorTotalVenda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorTotalVenda.setText("0,00");
        txtValorTotalVenda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtValorTotalVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorTotalVenda.setEnabled(false);

        txtValorDescontoVenda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorDescontoVenda.setText("0,00");
        txtValorDescontoVenda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtValorDescontoVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorDescontoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorDescontoVendaActionPerformed(evt);
            }
        });

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(255, 255, 255));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setText("0,00");
        jTextField9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField9.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField9.setEnabled(false);
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(255, 255, 255));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField10.setText("0,00");
        jTextField10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField10.setEnabled(false);

        jLabel1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total geral");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Desconto");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel3.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total pago");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel4.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Saldo final");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btNovaVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(jTextField9)
                            .addComponent(txtValorDescontoVenda, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtValorTotalVenda, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtValorTotalVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorDescontoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btSalvarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNovaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BackgroundLayout.createSequentialGroup()
                        .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(BackgroundLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BackgroundLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAumentarQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAumentarQtdActionPerformed
        aumentarQtd();
    }//GEN-LAST:event_btAumentarQtdActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void btNovoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoProdutoActionPerformed
        System.out.println("br.unipar.pdvsistema.tela.MainControlador.btNovoProdutoActionPerformed()");
    }//GEN-LAST:event_btNovoProdutoActionPerformed

    private void txtValorDescontoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorDescontoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorDescontoVendaActionPerformed

    private void btDiminuirQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDiminuirQtdActionPerformed
        diminuirQtd();
    }//GEN-LAST:event_btDiminuirQtdActionPerformed

    private void btNovoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btNovoClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btNovoClienteKeyPressed

    private void btNovoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoClienteActionPerformed
        
    }//GEN-LAST:event_btNovoClienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JButton btAdicionarProduto;
    private javax.swing.JButton btAumentarQtd;
    private javax.swing.JButton btDiminuirQtd;
    private javax.swing.JButton btNovaVenda;
    private javax.swing.JButton btNovoCliente;
    private javax.swing.JButton btNovoProduto;
    private javax.swing.JButton btSalvarVenda;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tabelaItens;
    private javax.swing.JTable tabelaPagamentos;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoProduto;
    private javax.swing.JTextField txtDescricaoProduto;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtValorDescontoVenda;
    private javax.swing.JTextField txtValorTotalVenda;
    // End of variables declaration//GEN-END:variables

}
