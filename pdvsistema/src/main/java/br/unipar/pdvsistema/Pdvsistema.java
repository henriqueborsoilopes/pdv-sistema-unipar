package br.unipar.pdvsistema;

import br.unipar.pdvsistema.tela.MainControlador;
import javax.swing.SwingUtilities;

public class Pdvsistema {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainControlador();
        });
    }
}
