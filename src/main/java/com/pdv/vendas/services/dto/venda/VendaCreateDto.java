package com.pdv.vendas.services.dto.venda;

import com.pdv.vendas.model.ItemVenda;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaCreateDto(
        String observacoes,
        @NotNull(message = "Necessário informar os itens da venda")
        List<ItemVenda> itens,
        @NotBlank(message="Necessário informar o id do cliente")
        Long clienteId
        ) {
}
