package com.pdv.vendas.services.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteUpdateDto(
        @NotNull(message = "ID não pode ser nulo")
        Long id,
        @NotNull(message = "Nome não pode ser nulo")
        @NotBlank(message = "Nome não pode ser vazio")
        String nome,
        @NotNull(message = "Telefone não pode ser nulo")
        @NotBlank(message = "Telefone não pode ser vazio")
        String telefone,
        @NotNull(message = "Email não pode ser nulo")
        @NotBlank(message = "Email não pode ser vazio")
        String email) {
}
