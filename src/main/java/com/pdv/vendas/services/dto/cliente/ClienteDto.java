package com.pdv.vendas.services.dto.cliente;

public record ClienteDto(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
