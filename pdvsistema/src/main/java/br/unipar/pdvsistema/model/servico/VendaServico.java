package br.unipar.pdvsistema.model.servico;

import br.unipar.pdvsistema.model.entidade.Venda;
import br.unipar.pdvsistema.model.repositorio.VendaRepositorio;
import br.unipar.pdvsistema.model.servico.excecao.BancoDadosExcecao;
import br.unipar.pdvsistema.model.servico.excecao.ValidacaoExcecao;
import br.unipar.pdvsistema.model.servico.infra.ConexaoBD;
import br.unipar.pdvsistema.model.servico.infra.ControladorBD;
import br.unipar.pdvsistema.model.servico.infra.TransacaoBD;
import br.unipar.pdvsistema.model.servico.validacao.VendaValidacao;

public class VendaServico {
    
    private final VendaRepositorio vendaRepository;
    
    public VendaServico(VendaRepositorio vendaRepository) {
        this.vendaRepository = vendaRepository;
    }
    
    public Venda inserir(Venda venda) throws BancoDadosExcecao, ValidacaoExcecao {
        VendaValidacao.validarVenda(venda);
        try {
            TransacaoBD.getTransaction().begin();
            venda = vendaRepository.inserir(venda);
            TransacaoBD.getTransaction().commit();
            return venda;
        } catch (Exception e) {
            TransacaoBD.getTransaction().rollback();
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        } finally {
            ControladorBD.closeEntityManager();
            ConexaoBD.closeEntityManagerFactory();
        }
    }
}
