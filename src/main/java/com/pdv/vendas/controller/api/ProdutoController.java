package com.pdv.vendas.controller.api;

import com.pdv.vendas.services.ProdutoService;
import com.pdv.vendas.services.dto.produto.ProdutoCreateDto;
import com.pdv.vendas.services.dto.produto.ProdutoDto;
import com.pdv.vendas.services.dto.produto.ProdutoUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api") // Changed path to "/api/produtos"
public class ProdutoController {

    private ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @PostMapping("produto")
    public ResponseEntity<ProdutoDto> createProduto(@RequestBody @Valid ProdutoCreateDto produtoDto, UriComponentsBuilder uriComponentsBuilder){
        ProdutoDto produtoResponse = produtoService.createProduto(produtoDto);
        var uri = uriComponentsBuilder.path("/api/produtos/{id}").buildAndExpand(produtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(produtoResponse);
    }

    @GetMapping("produto")
    public ResponseEntity<List<ProdutoDto>> getAllProdutos() {
        List<ProdutoDto> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("produto/{id}")
    public ResponseEntity<ProdutoDto> getProdutoById(@PathVariable Long id) {
        return produtoService.getProdutoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("produto/categoria/{categoria}")
    public ResponseEntity<List<ProdutoDto>> getProdutoByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(produtoService.getProdutoByCategoria(categoria));
    }

    @GetMapping("produto/descricao/{descricao}")
    public ResponseEntity<List<ProdutoDto>> getProdutoByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(produtoService.getProdutoByDescricao(descricao));
    }

    @PutMapping("produto/{id}")
    public ResponseEntity<ProdutoDto> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoUpdateDto produtoUpdateDto) {
            ProdutoDto updatedProduto = produtoService.updateProduto(id, produtoUpdateDto);
            return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("produto/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }



}