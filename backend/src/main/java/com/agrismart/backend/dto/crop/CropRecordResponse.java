package com.agrismart.backend.dto.crop;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CropRecordResponse(
        UUID id,
        UUID farmId,
        UUID cropId,
        String cropName,
        LocalDate plantingDate,
        LocalDate expectedHarvestDate,
        LocalDate actualHarvestDate,
        String season,
        String status,
        Double expectedYieldQuintal,
        Double actualYieldQuintal,
        String notes,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}