package com.mycompany.pdv.services;

import com.mycompany.pdv.client.VendaRestClient;
import com.mycompany.pdv.dao.VendaDAO;

import com.mycompany.pdv.modelos.ItemVenda;
import com.mycompany.pdv.modelos.Venda;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VendaService {

    private VendaDAO dao;
    private VendaRestClient client = new VendaRestClient();
    private ProdutoService produtoService;

    public VendaService(ProdutoService produtoService, VendaDAO vendaDAO) {
        this.produtoService = produtoService;
        this.dao = vendaDAO;
    }

    public Venda registrarVenda(Venda venda) throws IOException {
        registroLocal(venda);
        return client.createProduto(venda);
    }

    private void registroLocal(Venda venda) {
        venda.setDataVenda(new Date());
        venda.setTotal(calcularTotal(venda.getItens()));
        dao.inserir(venda);
    }

    private BigDecimal calcularTotal(List<ItemVenda> itens) {
        BigDecimal valor = BigDecimal.ZERO;
        if ((itens != null && itens.size() > 0)) {
            for (ItemVenda item : itens) {
                valor = valor.add(item.getTotal());
            }
        }
        return valor;
    }

}
