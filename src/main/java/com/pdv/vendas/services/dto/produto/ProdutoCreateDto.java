package com.pdv.vendas.services.dto.produto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProdutoCreateDto(@NotBlank(message = "A categoria não pode ser vazia")
                               String categoria,
                               @DecimalMax(value = "9999999999.99", message = "Limite decimal de valor ultrapassado")
                               BigDecimal valor,
                               @NotBlank(message = "A descrição não pode ser vazia")
                               String descricao){
}
