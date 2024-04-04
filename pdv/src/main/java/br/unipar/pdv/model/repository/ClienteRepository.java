package br.unipar.pdv.model.repository;

import br.unipar.pdv.model.entity.Cliente;
import br.unipar.pdv.model.factory.ManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteRepository {
    
    public List<Cliente> acharTodosPaginado(String nome, int qtdRegistro, int pagina) {
        EntityManager em = ManagerFactory.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Cliente obj WHERE obj.nome LIKE :nome";
        TypedQuery<Cliente> authorQuery = em.createQuery(query, Cliente.class);
        authorQuery.setParameter("nome", "%" + nome + "%");
        
        ManagerFactory.getCloseEntityManager();
        
        return authorQuery.setFirstResult(qtdRegistro).setMaxResults(pagina).getResultList();
    }
}
