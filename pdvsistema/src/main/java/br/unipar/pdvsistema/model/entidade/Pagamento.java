package br.unipar.pdvsistema.model.entidade;

import br.unipar.pdvsistema.model.entidade.enums.TipoPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer parcelas;
    private Double valorPago;
    private Integer tipoPagamento;
    
    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;
    
    public Pagamento() { }

    public Pagamento(Long id, Integer parcelas, Double valorPago, TipoPagamento tipoPagamento, Venda venda) {
        this.id = id;
        this.parcelas = parcelas;
        this.valorPago = valorPago;
        this.tipoPagamento = tipoPagamento.getCodigo();
        this.venda = venda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public TipoPagamento getTipoPagamento() {
        return TipoPagamento.paraEnum(tipoPagamento);
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento.getCodigo();
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pagamento other = (Pagamento) obj;
        return Objects.equals(this.id, other.id);
    }
}
