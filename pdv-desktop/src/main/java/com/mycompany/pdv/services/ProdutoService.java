package com.mycompany.pdv.services;

import com.mycompany.pdv.client.ProdutoRestClient;
import com.mycompany.pdv.modelos.Produto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProdutoService {
    ProdutoRestClient client = new ProdutoRestClient();
    
    public ProdutoService(){
    }

    public List<Produto> listarProduto() throws IOException {
        return  Arrays.stream(client.getAllProdutos()).toList();
    }

    public void salvar(Produto produto) throws IOException {
        client.createProduto(produto);
    }

    public void atualizar(Produto produto) throws IOException {
        client.updateProduto(produto,produto.getId());
    }

    public Produto obterProdutoPorId(Long id) throws IOException {
        Produto produto = client.getProdutoById(id);
        return produto;
    }

    public List<Produto> obterProdutoPorDescricao(String descricao) throws IOException {
        return Arrays.stream(client.getProdutoByDescricao(descricao)).toList();
    }

    public List<Produto> obterProdutoPorCategoria(String categoria) throws IOException {
        return Arrays.stream(client.getProdutoByCategoria(categoria)).toList();
    }

    public Boolean removerProduto(Long id) throws IOException {
        client.deleteProdutoById(id);
        return true;
    }
}
