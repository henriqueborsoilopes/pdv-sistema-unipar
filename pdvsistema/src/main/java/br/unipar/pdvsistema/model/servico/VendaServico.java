package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.model.entidade.Cliente;
import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.repositorio.VendaRepositorio;
import br.unipar.pdvsistema.model.servico.excecao.BancoDadosExcecao;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import br.unipar.pdvsistema.model.servico.validacao.VendaValidacao;

public class VendaServico {
    
    private final VendaRepositorio vendaRepository;
    
    public VendaServico(VendaRepositorio vendaRepository) {
        this.vendaRepository = vendaRepository;
    }
    
    public Venda inserir(Venda venda) throws BancoDadosExcecao, ValidacaoExcecao {
        VendaValidacao.validarVenda(venda);
        if (venda.getCliente() == null) {
            venda.setCliente(new Cliente(null, "Consumidor Geral", null, null));
        }
        try {
            ConexaoBD.getEntityManager().getTransaction().begin();
            venda = vendaRepository.inserir(venda);
            ConexaoBD.getEntityManager().getTransaction().commit();
            return venda;
        } catch (Exception e) {
            ConexaoBD.getEntityManager().getTransaction().rollback();
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        } finally {
            ConexaoBD.closeEntityManager();
        }
    }
}
