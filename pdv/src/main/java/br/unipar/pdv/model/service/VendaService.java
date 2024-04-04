package br.unipar.pdv.model.service;

import br.unipar.pdv.model.entity.ItemVenda;
import br.unipar.pdv.model.entity.Venda;
import br.unipar.pdv.model.factory.TransactionFactory;
import br.unipar.pdv.model.repository.ItemVendaRepository;
import br.unipar.pdv.model.repository.VendaRepository;
import br.unipar.pdv.model.service.excecao.BancoDadosExcecao;
import br.unipar.pdv.model.service.excecao.ValidacaoExcecao;
import br.unipar.pdv.model.service.validacao.VendaValidacao;

public class VendaService {
    
    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;
    
    public VendaService(VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }
    
    public void inserir(Venda venda) throws BancoDadosExcecao, ValidacaoExcecao {
        VendaValidacao.validarVenda(venda);
        try {
            TransactionFactory.getTransaction().begin();
            venda = vendaRepository.inserir(venda);
            for (ItemVenda item : venda.getItens()) {
                itemVendaRepository.inserir(item);
            }
            TransactionFactory.getTransaction().commit();
        } catch (Exception e) {
            TransactionFactory.getTransaction().rollback();
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        }
    }
}
