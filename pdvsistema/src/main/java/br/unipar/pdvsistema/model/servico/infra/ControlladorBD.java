package br.unipar.pdvsistema.model.servico.infra;

import jakarta.persistence.EntityManager;

public class ControlladorBD {
        
    private static EntityManager em;
    
    public ControlladorBD() { }
    
    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = ConexaoBD.getEntityManagerFactory().createEntityManager();
            System.out.println("entity manager aberta!");
        }
        return em;
    }
    
    public static void getCloseEntityManager() {
        if (em != null) {
            em = null;
        }
    }
}
