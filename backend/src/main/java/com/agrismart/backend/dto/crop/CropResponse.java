package com.agrismart.backend.dto.crop;

import java.time.LocalDateTime;
import java.util.UUID;

public record CropResponse(
        UUID id,
        String name,
        String scientificName,
        String category,
        String description,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}