package com.thiago.epost.post.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(

        @NotBlank(message = "Por favor insira um título para o post.")
        String title,

        @NotBlank(message = "Por favor insira um conteúdo para o post.")
        String content
) {}
