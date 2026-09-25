package com.agrismart.backend.dto.crop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CropRequest(

        @NotBlank(message = "Crop name is required")
        @Size(max = 120)
        String name,

        @Size(max = 120)
        String scientificName,

        @Size(max = 100)
        String category,

        @Size(max = 1000)
        String description
) {
}