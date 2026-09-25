package com.agrismart.backend.dto.crop;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record CropRecordRequest(

        @NotNull(message = "Crop ID is required")
        UUID cropId,

        @NotNull(message = "Planting date is required")
        LocalDate plantingDate,

        LocalDate expectedHarvestDate,

        LocalDate actualHarvestDate,

        @Size(max = 50)
        String season,

        @Size(max = 50)
        String status,

        @DecimalMin(
                value = "0.0",
                message = "Expected yield cannot be negative"
        )
        Double expectedYieldQuintal,

        @DecimalMin(
                value = "0.0",
                message = "Actual yield cannot be negative"
        )
        Double actualYieldQuintal,

        @Size(max = 1000)
        String notes
) {
}