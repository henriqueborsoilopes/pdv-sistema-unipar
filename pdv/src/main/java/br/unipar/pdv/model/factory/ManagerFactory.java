package br.unipar.pdv.model.factory;

import jakarta.persistence.EntityManager;

public class ManagerFactory {
        
    private static EntityManager em;
    
    public ManagerFactory() { }
    
    public static EntityManager getEntityManager() {
        if(em == null || !em.isOpen()) {
            em = ConectionFactory.getEntityManagerFactory().createEntityManager();
            System.out.println("entity manager aberta!");
        }
        return em;
    }
    
    public static void getCloseEntityManager() {
        if (em != null) {
            em.close();
        }
    }
}
