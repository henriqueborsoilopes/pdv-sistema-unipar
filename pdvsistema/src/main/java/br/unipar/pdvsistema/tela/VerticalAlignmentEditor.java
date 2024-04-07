package br.unipar.pdvsistema.tela;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VerticalAlignmentEditor extends DefaultCellEditor {
    
    public VerticalAlignmentEditor(int alignment) {
        super(new JTextField());
        JTextField textField = (JTextField) getComponent();
        textField.setHorizontalAlignment(alignment);
        textField.setBorder(null);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component editorComponent = super.getTableCellEditorComponent(table, value, isSelected, row, column);
        if (editorComponent instanceof JTextField textField) {
            textField.setHorizontalAlignment(((JTextField) getComponent()).getHorizontalAlignment());
        }
        return editorComponent;
    }
}
