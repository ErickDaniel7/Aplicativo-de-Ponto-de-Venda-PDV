package com.pdv.vendas.services.dto.venda;

import java.math.BigDecimal;
import java.util.Date;

public record VendaDto(Long id, Long clienteId, String observacoes, Date dataVenda, BigDecimal total) {}
