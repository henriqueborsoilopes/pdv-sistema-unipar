package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.model.entidade.Produto;
import br.unipar.pdvsistema.model.repositorio.ProdutoRepositorio;
import java.util.List;

public class ProdutoServico {
    
    private final ProdutoRepositorio repository;
    
    public ProdutoServico(ProdutoRepositorio repository) {
        this.repository = repository;
    }
    
    public List<Produto> acharTodosPaginado(String descricao, int qtdRegistro, int pagina) {
        return repository.acharTodosPaginado(descricao, qtdRegistro, pagina);
    }
}
