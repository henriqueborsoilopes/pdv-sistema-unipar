package br.unipar.pdvsistema.model.servico.infra;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexaoBD {
    
    private static EntityManagerFactory emf;
    
    public ConexaoBD() { }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("br.unipar.pdvsistema");
            System.out.println("conexão aberta!");
        }
        return emf;
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf = null;
            System.out.println("conexão fechada!");
        }
    }
}