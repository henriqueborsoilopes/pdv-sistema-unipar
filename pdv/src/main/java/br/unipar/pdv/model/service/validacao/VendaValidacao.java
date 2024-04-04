package br.unipar.pdv.model.service.validacao;

import br.unipar.pdv.model.entity.Venda;
import br.unipar.pdv.model.service.excecao.ValidacaoExcecao;

public class VendaValidacao {
    
    public static void validarVenda(Venda venda) throws ValidacaoExcecao {
        ValidacaoExcecao erro = new ValidacaoExcecao("Validação");
        throw erro;
    }
}
