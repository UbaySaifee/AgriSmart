package com.agrismart.backend.dto.farmer;

import com.agrismart.backend.entity.FarmingType;
import com.agrismart.backend.entity.IrrigationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FarmerProfileRequest(

        @Size(max = 120)
        String village,

        @Size(max = 120)
        String district,

        @Size(max = 120)
        String state,

        @Pattern(
                regexp = "^$|^[0-9]{6}$",
                message = "Pincode must contain exactly 6 digits"
        )
        String pincode,

        FarmingType farmingType,

        IrrigationType irrigationType,

        @DecimalMin(
                value = "0.0",
                message = "Land area cannot be negative"
        )
        Double totalLandAcres,

        @Min(
                value = 0,
                message = "Experience cannot be negative"
        )
        Integer farmingExperienceYears
) {
}
