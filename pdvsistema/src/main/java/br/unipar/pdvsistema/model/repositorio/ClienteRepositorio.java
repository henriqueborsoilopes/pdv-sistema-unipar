package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.dto.PaginaDTO;
import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteRepositorio {
    
    public PaginaDTO acharTodosPaginado(String nome, int numPagina, int tamPagina) {
        EntityManager em = ConexaoBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Cliente obj WHERE obj.nome LIKE :nome ORDER BY obj.nome ASC";
        TypedQuery<Cliente> authorQuery = em.createQuery(query, Cliente.class);
        authorQuery.setParameter("nome", "%" + nome + "%");
        authorQuery.setFirstResult(numPagina * tamPagina);
        authorQuery.setMaxResults(tamPagina);
        
        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(obj) FROM Cliente obj WHERE obj.nome LIKE :nome", Long.class);
        countQuery.setParameter("nome", "%" + nome + "%");
        long totalElementos = countQuery.getSingleResult();
        
        List<Cliente> clientes = authorQuery.getResultList();
        
        ConexaoBD.closeEntityManager();
        
        PaginaDTO<Cliente> entidades = new PaginaDTO(numPagina, tamPagina, totalElementos);
        entidades.getConteudo().addAll(clientes);
        
        return entidades;
    }
}
