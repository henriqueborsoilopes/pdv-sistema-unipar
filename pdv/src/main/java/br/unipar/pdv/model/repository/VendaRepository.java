package br.unipar.pdv.model.repository;

import br.unipar.pdv.model.entity.Venda;
import br.unipar.pdv.model.factory.ManagerFactory;
import jakarta.persistence.EntityManager;

public class VendaRepository {

    public Venda inserir(Venda venda) {
        EntityManager em = ManagerFactory.getEntityManager();
        em.persist(venda);
        ManagerFactory.getCloseEntityManager();
        return venda;
    }
}
