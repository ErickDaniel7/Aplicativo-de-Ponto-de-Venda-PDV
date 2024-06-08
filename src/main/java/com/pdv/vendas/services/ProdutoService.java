package com.pdv.vendas.services;

import com.pdv.vendas.exceptions.ProdutoNotFoundException;
import com.pdv.vendas.repository.ProdutoRepository;
import com.pdv.vendas.services.dto.produto.ProdutoCreateDto;
import com.pdv.vendas.services.dto.produto.ProdutoDto;
import com.pdv.vendas.services.dto.produto.ProdutoUpdateDto;
import com.pdv.vendas.services.util.mapper.ProdutoMapper;
import com.pdv.vendas.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDto createProduto(ProdutoCreateDto produtoCreateDto) {
        Produto produto = ProdutoMapper.INSTANCE.toEntity(produtoCreateDto);
        Produto savedProduto = produtoRepository.save(produto);
        return ProdutoMapper.INSTANCE.toDto(savedProduto);
    }

    public List<ProdutoDto> getAllProdutos() {
        return produtoRepository.findAll().stream()
                .map(produto -> ProdutoMapper.INSTANCE.toDto(produto))
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDto> getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ProdutoMapper.INSTANCE.toDto(produto));
    }

    public List<ProdutoDto> getProdutoByDescricao(String descricao){
        return produtoRepository.findProdutoByDescricao(descricao)
                .stream()
                .map(produto -> ProdutoMapper.INSTANCE.toDto(produto))
                .collect(Collectors.toList());
    }

    public List<ProdutoDto> getProdutoByCategoria(String descricao){
        return produtoRepository.findProdutoByCategoria(descricao)
                .stream()
                .map(produto -> ProdutoMapper.INSTANCE.toDto(produto))
                .collect(Collectors.toList());
    }


    public ProdutoDto updateProduto(Long id, ProdutoUpdateDto produtoUpdateDto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produtoUpdate = ProdutoMapper.INSTANCE.toEntity(produtoUpdateDto);
            produtoUpdate.setId(id); // Set the original ID to avoid creating a new product
            return ProdutoMapper.INSTANCE.toDto(produtoRepository.save(produtoUpdate));
        } else {
            throw new ProdutoNotFoundException("Produto não encontrado com id " + id);
        }
    }

    public void deleteProduto(Long id) {
        Optional<Produto> produtoOptional =  produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            produtoRepository.deleteById(id);
        }
        else{
            throw new ProdutoNotFoundException("Produto não existe!");
        }
    }

}
