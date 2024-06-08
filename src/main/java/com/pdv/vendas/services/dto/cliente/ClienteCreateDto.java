package com.pdv.vendas.services.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteCreateDto(
        @NotBlank(message = "Nome não pode ser vazio")
        @NotNull(message = "Nome de cliente não pode ser nulo")
        String nome,

        @NotBlank(message = "Telefone não pode ser vazio")
        @NotNull(message = "Nome de cliente não pode ser nulo")
        String telefone,

        @NotBlank(message="Email não pode ser vezio")
        @NotNull(message = "Email de cliente não pode ser nulo")
        String email) {
}
