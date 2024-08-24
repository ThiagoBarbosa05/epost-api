package com.thiago.epost.user.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticateRequestDTO(

        @NotBlank(message = "Insira um e-mail.")
        String email,

        @NotBlank(message = "Insira a senha.")
        String password
) {
}
