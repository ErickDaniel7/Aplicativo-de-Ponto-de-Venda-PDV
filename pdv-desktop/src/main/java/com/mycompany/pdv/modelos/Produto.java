package com.mycompany.pdv.modelos;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String categoria;

    public Produto(Long id, String descricao, String categoria, BigDecimal valor) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
    }

    public Produto(String descricao, String categoria, BigDecimal valor) {
        this.id = null;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
    }

    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) && Objects.equals(descricao, produto.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao);
    }
}
