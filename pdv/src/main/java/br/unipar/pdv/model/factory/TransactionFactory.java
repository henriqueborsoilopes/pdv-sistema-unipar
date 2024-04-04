package br.unipar.pdv.model.factory;

import jakarta.persistence.EntityTransaction;

public class TransactionFactory {
    
    private static EntityTransaction transaction;
    
    public static EntityTransaction getTransaction() {
        transaction = ManagerFactory.getEntityManager().getTransaction();
        return transaction;
    }
}
