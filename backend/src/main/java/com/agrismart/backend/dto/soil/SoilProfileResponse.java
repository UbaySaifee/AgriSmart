package com.agrismart.backend.dto.soil;

import java.time.LocalDateTime;
import java.util.UUID;

public record SoilProfileResponse(

        UUID id,
        UUID farmId,
        String soilType,
        Double ph,
        Double nitrogen,
        Double phosphorus,
        Double potassium,
        Double organicCarbon,
        Double electricalConductivity,
        Double moisturePercentage,
        String notes,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}