package com.pdv.vendas.services.util.mapper;



import com.pdv.vendas.model.Produto;
import com.pdv.vendas.services.dto.produto.ProdutoCreateDto;
import com.pdv.vendas.services.dto.produto.ProdutoDto;
import com.pdv.vendas.services.dto.produto.ProdutoUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
    Produto toEntity(ProdutoDto clienteDTO);
    Produto toEntity (ProdutoUpdateDto clienteUpdateDto);
    Produto toEntity (ProdutoCreateDto produtoCreateDto);
    ProdutoDto toDto(Produto produto);
}
