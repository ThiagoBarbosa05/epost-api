package com.thiago.epost.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(

        @NotBlank(message = "Insira seu nome")
        String name,

        @NotBlank
        @Email(message = "Insira um e-mail válido")
        String email,

        @NotBlank(message = "A senha precisa ter no mínimo 6 caracteres.")
        @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
        String password
) {}
