package com.pdv.vendas.services.util.mapper;

import com.pdv.vendas.model.Cliente;
import com.pdv.vendas.services.dto.cliente.ClienteCreateDto;
import com.pdv.vendas.services.dto.cliente.ClienteDto;
import com.pdv.vendas.services.dto.cliente.ClienteUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    Cliente toEntity(ClienteDto clienteDTO);
    Cliente toEntity (ClienteUpdateDto clienteUpdateDto);
    Cliente toEntity (ClienteCreateDto clienteCreateDto);
    ClienteDto toDto(Cliente cliente);
}