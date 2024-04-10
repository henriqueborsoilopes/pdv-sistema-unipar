package br.unipar.pdvsistema.model.entidade.enums;

public enum TipoPagamento {
    
    DINHEIRO(1, "Dinheiro", false),
    CARTAO_CREDITO(2, "Cartão de Crédito", true),
    CARTAO_DEBITO(3, "Cartão de Débito", true),
    PIX(4, "Pix", false);
    
    private Integer codigo;
    private String descricao;
    private boolean permiteParcelar;
    
    private TipoPagamento(Integer codigo, String descricao, boolean permiteParcelar) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.permiteParcelar = permiteParcelar;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPermiteParcelar() {
        return permiteParcelar;
    }

    public void setPermiteParcelar(boolean permiteParcelar) {
        this.permiteParcelar = permiteParcelar;
    }
    
    public static TipoPagamento paraEnum(Integer codigo){ 
        if (codigo == null) {
            return null;
        }
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código inválido! " + codigo);
    }
}
