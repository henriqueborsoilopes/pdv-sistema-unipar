package br.unipar.pdvsistema.tela.tabelaclientes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ClienteButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private final JButton button;
    private boolean isSelected;

    public ClienteButtonEditor() {
        button = new JButton();
        button.addActionListener((ActionEvent e) -> {
            // Lógica de seleção do botão
            isSelected = !isSelected;
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.isSelected = (Boolean) value;
        button.setText(isSelected ? "Selecionado" : "Selecionar");
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return isSelected;
    }
}
