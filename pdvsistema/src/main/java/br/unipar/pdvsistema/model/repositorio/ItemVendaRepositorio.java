package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.ItemVenda;
import br.unipar.pdvsistema.model.servico.infra.ControlladorBD;
import jakarta.persistence.EntityManager;

public class ItemVendaRepositorio {

    public void inserir(ItemVenda item) {
        EntityManager em = ControlladorBD.getEntityManager();
        em.persist(item);
        ControlladorBD.getCloseEntityManager();
    }
}
