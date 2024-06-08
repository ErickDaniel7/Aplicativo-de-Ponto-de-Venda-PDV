package com.pdv.vendas.repository;

import com.pdv.vendas.model.Produto;
import com.pdv.vendas.services.dto.produto.ProdutoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findProdutoByCategoria(String categoria);
    List<Produto> findProdutoByDescricao(String descricao);
}
