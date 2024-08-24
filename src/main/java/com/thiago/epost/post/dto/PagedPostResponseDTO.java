package com.thiago.epost.post.dto;

import java.util.List;

public record PagedPostResponseDTO(
        List<PostResponseDTO> content,
        PageMetadataDTO page
) {
}
