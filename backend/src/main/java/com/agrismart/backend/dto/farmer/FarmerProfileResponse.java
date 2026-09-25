package com.agrismart.backend.dto.farmer;

import com.agrismart.backend.entity.FarmingType;
import com.agrismart.backend.entity.IrrigationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record FarmerProfileResponse(
        UUID id,
        UUID userId,
        String village,
        String district,
        String state,
        String pincode,
        FarmingType farmingType,
        IrrigationType irrigationType,
        Double totalLandAcres,
        Integer farmingExperienceYears,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
