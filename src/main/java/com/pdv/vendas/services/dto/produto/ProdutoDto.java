package com.pdv.vendas.services.dto.produto;

import java.math.BigDecimal;

public record ProdutoDto(Long id, String categoria, BigDecimal valor, String descricao) {
}
