package com.thiago.epost.post.dto;

public record PageMetadataDTO(
        int size,
        int page,
        long total,
        int totalPages
) {
}
