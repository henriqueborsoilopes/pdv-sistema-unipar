package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.repositorio.ClienteRepositorio;
import java.util.List;

public class ClienteServico {
   
    private final ClienteRepositorio repository;
    
    public ClienteServico(ClienteRepositorio repository) {
        this.repository = repository;
    }
    
    public List<Cliente> acharTodosPaginado(String nome, int qtdRegistro, int pagina) {
        return repository.acharTodosPaginado(nome, qtdRegistro, pagina);
    }
}
