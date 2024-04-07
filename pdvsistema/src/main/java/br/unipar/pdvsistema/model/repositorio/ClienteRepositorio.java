package br.unipar.pdvsistema.model.repositorio;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import br.unipar.pdvsistema.model.servico.infra.ControladorBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteRepositorio {
    
    public List<Cliente> acharTodosPaginado(String nome, int qtdRegistro, int pagina) {
        EntityManager em = ControladorBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Cliente obj WHERE obj.nome LIKE :nome ORDER BY obj.nome ASC";
        TypedQuery<Cliente> authorQuery = em.createQuery(query, Cliente.class);
        authorQuery.setParameter("nome", "%" + nome + "%");
        authorQuery.setFirstResult(qtdRegistro);
        authorQuery.setMaxResults(pagina);
        
        List<Cliente> clientes = authorQuery.getResultList();
        
        ControladorBD.closeEntityManager();
        ConexaoBD.closeEntityManagerFactory();
        
        return clientes;
    }
}
