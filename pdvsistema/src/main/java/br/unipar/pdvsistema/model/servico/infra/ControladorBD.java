package br.unipar.pdvsistema.model.servico.infra;

import jakarta.persistence.EntityManager;

public class ControladorBD {
        
    private static EntityManager em;
    
    public ControladorBD() { }
    
    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = ConexaoBD.getEntityManagerFactory().createEntityManager();
            System.out.println("entity manager aberta!");
        }
        return em;
    }
    
    public static void closeEntityManager() {
        if (em != null) {
            em = null;
        }
    }
}
