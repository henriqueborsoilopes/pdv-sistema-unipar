package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.servico.infra.ControlladorBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteRepositorio {
    
    public List<Cliente> acharTodosPaginado(String nome, int qtdRegistro, int pagina) {
        EntityManager em = ControlladorBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Cliente obj WHERE obj.nome LIKE :nome";
        TypedQuery<Cliente> authorQuery = em.createQuery(query, Cliente.class);
        authorQuery.setParameter("nome", "%" + nome + "%");
        
        ControlladorBD.getCloseEntityManager();
        
        return authorQuery.setFirstResult(qtdRegistro).setMaxResults(pagina).getResultList();
    }
}
