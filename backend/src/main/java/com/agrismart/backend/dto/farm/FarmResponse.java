package com.agrismart.backend.dto.farm;

import com.agrismart.backend.entity.IrrigationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record FarmResponse(
        UUID id,
        UUID userId,
        String farmName,
        String village,
        String district,
        String state,
        String pincode,
        Double areaAcres,
        String soilType,
        IrrigationType irrigationType,
        Double latitude,
        Double longitude,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
