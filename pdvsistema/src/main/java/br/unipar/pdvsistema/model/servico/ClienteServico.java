package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.dto.PaginaDTO;
import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.repositorio.ClienteRepositorio;

public class ClienteServico {
   
    private final ClienteRepositorio repository;
    
    public ClienteServico(ClienteRepositorio repository) {
        this.repository = repository;
    }
    
    public PaginaDTO<Cliente> acharTodosPaginado(String nome, int numPagina, int tamPagina) {
        return repository.acharTodosPaginado(nome, numPagina, tamPagina);
    }
}
