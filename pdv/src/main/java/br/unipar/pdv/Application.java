package br.unipar.pdv;

import br.unipar.pdv.view.frame.MainController;
import javax.swing.SwingUtilities;

public class Application {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainController().setVisible(true);
        });
    }
}
