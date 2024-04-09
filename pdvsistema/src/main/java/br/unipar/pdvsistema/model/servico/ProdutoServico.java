package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.dto.PaginaDTO;
import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.repositorio.ProdutoRepositorio;

public class ProdutoServico {
    
    private final ProdutoRepositorio repository;
    
    public ProdutoServico(ProdutoRepositorio repository) {
        this.repository = repository;
    }
    
    public PaginaDTO<Produto> acharTodosPaginado(String nome, int numPagina, int tamPagina) {
        return repository.acharTodosPaginado(nome, numPagina, tamPagina);
    }
}
