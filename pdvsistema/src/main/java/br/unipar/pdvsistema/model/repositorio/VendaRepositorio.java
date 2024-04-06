package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.servico.infra.ControlladorBD;
import jakarta.persistence.EntityManager;

public class VendaRepositorio {

    public Venda inserir(Venda venda) {
        EntityManager em = ControlladorBD.getEntityManager();
        em.persist(venda);
        ControlladorBD.getCloseEntityManager();
        return venda;
    }
}
