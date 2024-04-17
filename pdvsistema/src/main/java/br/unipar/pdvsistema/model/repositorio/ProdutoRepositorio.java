package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.dto.PaginaDTO;
import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProdutoRepositorio {
    
    public PaginaDTO<Produto> acharTodosPaginado(String nome, int numPagina, int tamPagina) {        
        EntityManager em = ConexaoBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Produto obj WHERE obj.descricao LIKE :descricao ORDER BY obj.descricao ASC";
        TypedQuery<Produto> authorQuery = em.createQuery(query, Produto.class);
        authorQuery.setParameter("descricao", "%" + nome + "%");
        authorQuery.setFirstResult(numPagina);
        authorQuery.setMaxResults(tamPagina);
        
        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(obj) FROM Produto obj WHERE obj.descricao LIKE :nome", Long.class);
        countQuery.setParameter("nome", "%" + nome + "%");
        long totalElementos = countQuery.getSingleResult();
        
        List<Produto> produtos = authorQuery.getResultList();
        
        ConexaoBD.closeEntityManager();
        
        PaginaDTO<Produto> entidades = new PaginaDTO(numPagina, tamPagina, totalElementos);
        entidades.getConteudo().addAll(produtos);
        
        return entidades;
    }
}
