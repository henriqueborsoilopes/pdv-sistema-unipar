package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import jakarta.persistence.EntityManager;

public class VendaRepositorio {

    public Venda inserir(Venda venda) {
        EntityManager em = ConexaoBD.getEntityManager();
        venda = em.merge(venda);
        return venda;
    }
}
