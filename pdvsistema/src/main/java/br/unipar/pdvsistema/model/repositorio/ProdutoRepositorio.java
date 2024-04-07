package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import br.unipar.pdvsistema.model.servico.infra.ControlladorBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProdutoRepositorio {
    
    public List<Produto> acharTodosPaginado(String descricao, int qtdRegistro, int pagina) {
        EntityManager em = ControlladorBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Produto obj WHERE obj.descricao LIKE :descricao ORDER BY obj.descricao ASC";
        TypedQuery<Produto> authorQuery = em.createQuery(query, Produto.class);
        authorQuery.setParameter("descricao", "%" + descricao + "%");
        authorQuery.setFirstResult(qtdRegistro);
        authorQuery.setMaxResults(pagina);
        
        List<Produto> produtos = authorQuery.getResultList();
        
        ControlladorBD.getCloseEntityManager();
        ConexaoBD.closeEntityManagerFactory();
        
        return produtos;
    }
}
