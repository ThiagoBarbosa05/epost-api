package com.thiago.epost.auth.dto;



public record TokenResponseDTO(
        String accessToken,
        Long expiresAt
) {}
