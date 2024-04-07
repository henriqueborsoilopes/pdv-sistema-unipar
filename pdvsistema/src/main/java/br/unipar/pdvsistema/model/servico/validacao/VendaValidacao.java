package br.unipar.pdvsistema.model.servico.validacao;

import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;

public class VendaValidacao {
    
    public static void validarVenda(Venda venda) throws ValidacaoExcecao {
        ValidacaoExcecao erro = new ValidacaoExcecao("Validação");
        if (!erro.getErros().isEmpty()) {
            throw erro;
        }
    }
}
