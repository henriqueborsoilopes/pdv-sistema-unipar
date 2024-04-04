package br.unipar.pdv.model.factory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConectionFactory {
    
    private static EntityManagerFactory emf;
    
    public ConectionFactory() { }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("pdvsistema");
            System.out.println("conexão aberta!");
        }
        return emf;
    }
    
    public static void closeEntityManagerFactory() {
        if(emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("conexão fechada!");
        }
    }
}
