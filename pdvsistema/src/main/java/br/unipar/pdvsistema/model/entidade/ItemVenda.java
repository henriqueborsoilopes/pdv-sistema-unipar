package br.unipar.pdvsistema.model.entidade;

import br.unipar.pdvsistema.model.entidade.pk.ItemVendaPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_item_venda")
public class ItemVenda {
    
    @EmbeddedId
    private ItemVendaPK id = new ItemVendaPK();
    private String descricao;
    private Integer quantidade;
    private Double valorUnit;
    private Double desconto;
    
    public ItemVenda() { }

    public ItemVenda(Venda venda, Produto produto, String descricao, Integer quantidade, Double valorUnit, Double desconto) {
        this.id.setVenda(venda);
        this.id.setProduto(produto);
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnit = valorUnit;
        this.desconto = desconto;
    }
    
    public Venda getVenda() {
        return this.id.getVenda();
    }
    
    public void setVenda(Venda venda) {
        this.id.setVenda(venda);
    }
    
    public Produto getProduto() {
        return this.id.getProduto();
    }
    
    public void setProduto(Produto produto) {
        this.id.setProduto(produto);
    }
    
    public Double getValorTotalItem() {
        return (quantidade * valorUnit) - desconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(Double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }
}
