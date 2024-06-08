package com.mycompany.pdv.modelos;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemVenda {
    private Integer quantidade = 0;
    private Produto produto;
    private BigDecimal total = BigDecimal.ZERO;

    public ItemVenda() {
    }

    public ItemVenda(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.setTotal();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal() {
        if (this.getProduto() != null) {
            BigDecimal precoProduto = this.getProduto().getValor();
            int quantidade = this.getQuantidade();
            this.total = precoProduto.multiply(BigDecimal.valueOf(quantidade));
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.quantidade);
        hash = 29 * hash + Objects.hashCode(this.produto);
        hash = 29 * hash + Objects.hashCode(this.total);
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
        final ItemVenda other = (ItemVenda) obj;
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.produto.getId(), other.produto.getId())) {
            return false;
        }
        return Objects.equals(this.total, other.total);
    }


}
