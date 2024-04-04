package br.unipar.pdv.model.repository;

import br.unipar.pdv.model.entity.ItemVenda;
import br.unipar.pdv.model.factory.ManagerFactory;
import jakarta.persistence.EntityManager;

public class ItemVendaRepository {

    public void inserir(ItemVenda item) {
        EntityManager em = ManagerFactory.getEntityManager();
        em.persist(item);
        ManagerFactory.getCloseEntityManager();
    }
}
