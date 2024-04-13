package br.unipar.pdvsistema.model.servico.excecao;

public class ValidacaoExcecao extends Exception {
    
    private String campo;
    private String descricao;
    
    public ValidacaoExcecao(String campo, String descricao) {
        this.campo = campo;
        this.descricao = descricao;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
