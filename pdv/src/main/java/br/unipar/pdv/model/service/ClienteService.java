package br.unipar.pdv.model.service;

import br.unipar.pdv.model.entity.Cliente;
import br.unipar.pdv.model.repository.ClienteRepository;
import java.util.List;

public class ClienteService {
   
    private final ClienteRepository repository;
    
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    
    public List<Cliente> acharTodosPaginado(String nome, int qtdRegistro, int pagina) {
        return repository.acharTodosPaginado(nome, qtdRegistro, pagina);
    }
}
