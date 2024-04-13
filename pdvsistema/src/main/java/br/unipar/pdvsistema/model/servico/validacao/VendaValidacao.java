package br.unipar.pdvsistema.model.servico.validacao;

import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;

public class VendaValidacao {
    
    public static void validarVenda(Venda venda) throws ValidacaoExcecao {
        
        if (venda.getItens().isEmpty()) {
           throw new ValidacaoExcecao("Produto", "Por favor, insira um produto.");
        }
        
        if (venda.getPagamentos().isEmpty()) {
            throw new ValidacaoExcecao("Pagamento", "Por favor, insira um pagamento.");
        }
    }
}
