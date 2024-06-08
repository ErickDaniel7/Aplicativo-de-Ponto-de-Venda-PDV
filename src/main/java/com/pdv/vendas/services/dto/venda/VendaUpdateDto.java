package com.pdv.vendas.services.dto.venda;

import com.pdv.vendas.services.dto.ItemVenda.ItemVendaUpdateDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaUpdateDto(
        @NotNull(message = "ID não pode ser nulo")
        Long id,
        String observacoes,
        @NotNull(message="Lista de itens de venda não definida")
        List<ItemVendaUpdateDto> itens,
        @NotNull(message = "Id de cliente não definido")
        Long clienteId) {
}
