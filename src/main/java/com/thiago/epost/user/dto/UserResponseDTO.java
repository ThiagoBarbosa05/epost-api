package com.thiago.epost.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String profileImage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
