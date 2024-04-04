package br.unipar.pdv.model.repository;

import br.unipar.pdv.model.entity.Produto;
import br.unipar.pdv.model.factory.ManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProdutoRepository {
    
    public List<Produto> acharTodosPaginado(String descricao, int qtdRegistro, int pagina) {
        EntityManager em = ManagerFactory.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Produto obj WHERE obj.descricao LIKE :descricao";
        TypedQuery<Produto> authorQuery = em.createQuery(query, Produto.class);
        authorQuery.setParameter("descricao", "%" + descricao + "%");
        
        ManagerFactory.getCloseEntityManager();
                        
        return authorQuery.setFirstResult(qtdRegistro).setMaxResults(pagina).getResultList();
    }
}
