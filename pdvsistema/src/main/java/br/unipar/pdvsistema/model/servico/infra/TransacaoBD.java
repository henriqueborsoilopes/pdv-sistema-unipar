package br.unipar.pdvsistema.model.servico.infra;

import jakarta.persistence.EntityTransaction;

public class TransacaoBD {
    
    private static EntityTransaction transaction;
    
    public static EntityTransaction getTransaction() {
        transaction = ControladorBD.getEntityManager().getTransaction();
        return transaction;
    }
}
