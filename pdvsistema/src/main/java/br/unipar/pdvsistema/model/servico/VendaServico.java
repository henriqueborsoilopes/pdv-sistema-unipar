package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.model.entidade.ItemVenda;
import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.servico.infra.TransacaoBD;
import br.unipar.pdvsistema.model.repositorio.ItemVendaRepositorio;
import br.unipar.pdvsistema.model.repositorio.VendaRepositorio;
import br.unipar.pdvsistema.model.servico.excecao.BancoDadosExcecao;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;
import br.unipar.pdvsistema.model.servico.validacao.VendaValidacao;

public class VendaServico {
    
    private final VendaRepositorio vendaRepository;
    private final ItemVendaRepositorio itemVendaRepository;
    
    public VendaServico(VendaRepositorio vendaRepository, ItemVendaRepositorio itemVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }
    
    public void inserir(Venda venda) throws BancoDadosExcecao, ValidacaoExcecao {
        VendaValidacao.validarVenda(venda);
        try {
            TransacaoBD.getTransaction().begin();
            venda = vendaRepository.inserir(venda);
            for (ItemVenda item : venda.getItens()) {
                itemVendaRepository.inserir(item);
            }
            TransacaoBD.getTransaction().commit();
        } catch (Exception e) {
            TransacaoBD.getTransaction().rollback();
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        }
    }
}
