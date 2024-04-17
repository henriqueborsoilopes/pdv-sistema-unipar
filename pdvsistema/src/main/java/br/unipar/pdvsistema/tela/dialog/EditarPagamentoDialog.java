package br.unipar.pdvsistema.tela.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import br.unipar.pdvsistema.model.entidade.Pagamento;
import br.unipar.pdvsistema.tela.MainPagamentoTabelaModelo;

public class EditarPagamentoDialog extends JDialog {
    private Pagamento pagamento;
    private MainPagamentoTabelaModelo tabelaModelo;

    private JLabel lblForma;
    private JLabel lblParcela;
    private JLabel lblValorTotal;
    private JTextField txtParcela;
    private JTextField txtValorTotal;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public EditarPagamentoDialog(Pagamento pagamento, MainPagamentoTabelaModelo tabelaModelo) {
        this.pagamento = pagamento;
        this.tabelaModelo = tabelaModelo;

        initComponents();
        preencherCampos();

        setTitle("Editar Pagamento");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        lblForma = new JLabel("Forma: " + pagamento.getTipoPagamento().getDescricao());
        lblParcela = new JLabel("Parcela:");
        lblValorTotal = new JLabel("Valor Total:");

        txtParcela = new JTextField(10);
        txtValorTotal = new JTextField(10);

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
        panel.add(lblForma);
        panel.add(new JLabel());
        panel.add(lblParcela);
        panel.add(txtParcela);
        panel.add(lblValorTotal);
        panel.add(txtValorTotal);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void preencherCampos() {
        txtParcela.setText(String.valueOf(pagamento.getParcelas()));
        txtValorTotal.setText(String.valueOf(pagamento.getValorPago()));
    }

    private void salvarAlteracoes() {
        try {
            int parcela = Integer.parseInt(txtParcela.getText());
            double valorTotal = Double.parseDouble(txtValorTotal.getText());

            pagamento.setParcelas(parcela);
            pagamento.setValorPago(valorTotal);

            // Atualiza a tabela após as alterações
            tabelaModelo.fireTableDataChanged(); 

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
