package br.unipar.pdvsistema.tela.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import br.unipar.pdvsistema.model.entidade.ItemVenda;
import br.unipar.pdvsistema.tela.MainItemVendaTabelaModelo;

public class EditarItemVendaDialog extends JDialog {
    private ItemVenda item;
    private MainItemVendaTabelaModelo tabelaModelo;

    private JLabel lblDescricao;
    private JLabel lblQuantidade;
    private JLabel lblDesconto;
    private JTextField txtQuantidade;
    private JTextField txtDesconto;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public EditarItemVendaDialog(ItemVenda item, MainItemVendaTabelaModelo tabelaModelo) {
        this.item = item;
        this.tabelaModelo = tabelaModelo;

        initComponents();
        preencherCampos();

        setTitle("Editar Item de Venda");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        lblDescricao = new JLabel("Descrição: " + item.getDescricao());
        lblQuantidade = new JLabel("Quantidade:");
        lblDesconto = new JLabel("Desconto:");

        txtQuantidade = new JTextField(10);
        txtDesconto = new JTextField(10);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAlteracoes();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(lblDescricao);
        panel.add(new JLabel());
        panel.add(lblQuantidade);
        panel.add(txtQuantidade);
        panel.add(lblDesconto);
        panel.add(txtDesconto);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void preencherCampos() {
        txtQuantidade.setText(String.valueOf(item.getQuantidade()));
        txtDesconto.setText(String.valueOf(item.getDesconto()));
    }

    private void salvarAlteracoes() {
        try {
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            double desconto = Double.parseDouble(txtDesconto.getText());

            item.setQuantidade(quantidade);
            item.setDesconto(desconto);

            // Atualiza a tabela após as alterações
            tabelaModelo.fireTableDataChanged(); 

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
