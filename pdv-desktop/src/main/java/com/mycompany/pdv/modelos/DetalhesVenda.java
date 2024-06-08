package com.mycompany.pdv.modelos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class DetalhesVenda {

    private Long id;
    private List<ItemVenda> itensVenda;
    private Cliente cliente;
    private String observacoes;
    private Date dataVenda;
    private BigDecimal total;

    public DetalhesVenda() {
    }

    public DetalhesVenda(Long id, Cliente cliente, String observacoes, Date dataVenda, BigDecimal total, List<ItemVenda> itensVenda) {
        this.id = id;
        this.itensVenda = itensVenda;
        this.cliente = cliente;
        this.observacoes = observacoes;
        this.dataVenda = dataVenda;
        this.total = total;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


}
