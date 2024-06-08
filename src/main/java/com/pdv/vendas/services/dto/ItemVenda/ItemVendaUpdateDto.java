package com.pdv.vendas.services.dto.ItemVenda;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemVendaUpdateDto(
        @NotNull(message="Id do item não informado")
        Long id,
        @NotNull(message="ID da venda não informado")
        Long vendaId,
        @NotNull(message = "Id do produto não informado")
        Long produtoId,
        @NotNull(message="Quantidade ao produto não informado")
        Integer quantidade,
        @NotNull(message="Valor unitário do produto não informado")
        BigDecimal valorUnitario,
        @NotNull(message="Valor total do item não informado")
        BigDecimal valorTotal) {
}
