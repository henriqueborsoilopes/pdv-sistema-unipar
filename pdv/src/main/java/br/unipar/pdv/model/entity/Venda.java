package br.unipar.pdv.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_venda")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double desconto;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "venda")
    private List<ItemVenda> itens = new ArrayList<>();
    
    public Venda() { }

    public Venda(Long id, Double desconto, Cliente cliente) {
        this.id = id;
        this.desconto = desconto;
        this.cliente = cliente;
    }
    
    public Double getValorTotal() {
        double valorTotalItem = 0.0;
        for (ItemVenda item : itens) {
            valorTotalItem += item.getValorTotalItem();
        }
        return valorTotalItem - desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void addItem(ItemVenda item) {
        this.itens.add(item);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Venda other = (Venda) obj;
        return Objects.equals(this.id, other.id);
    }
}
