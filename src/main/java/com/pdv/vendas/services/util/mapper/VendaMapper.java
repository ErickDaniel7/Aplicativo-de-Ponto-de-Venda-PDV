package com.pdv.vendas.services.util.mapper;

import com.pdv.vendas.services.dto.venda.VendaCreateDto;
import com.pdv.vendas.services.dto.venda.VendaDto;
import org.mapstruct.Mapper;

import com.pdv.vendas.model.Venda;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendaMapper {

    VendaMapper INSTANCE = Mappers.getMapper(VendaMapper.class);

    Venda toEntity(VendaCreateDto vendaCreateDto);

     // Ignore itensVenda set during mapping
    @Mapping(source = "cliente.id", target = "clienteId")
    VendaDto toDto(Venda venda);
}