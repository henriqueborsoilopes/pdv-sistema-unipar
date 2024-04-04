package br.unipar.pdv.model.service;

import br.unipar.pdv.model.entity.Produto;
import br.unipar.pdv.model.repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {
    
    private final ProdutoRepository repository;
    
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }
    
    public List<Produto> acharTodosPaginado(String descricao, int qtdRegistro, int pagina) {
        return repository.acharTodosPaginado(descricao, qtdRegistro, pagina);
    }
}
