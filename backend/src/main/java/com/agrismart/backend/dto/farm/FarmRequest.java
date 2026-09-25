package com.agrismart.backend.dto.farm;

import com.agrismart.backend.entity.IrrigationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FarmRequest(

        @NotBlank(message = "Farm name is required")
        @Size(
                max = 120,
                message = "Farm name cannot exceed 120 characters"
        )
        String farmName,

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

        @DecimalMin(
                value = "0.01",
                message = "Farm area must be greater than zero"
        )
        @NotNull(message = "Farm area is required")
        Double areaAcres,

        @Size(max = 100)
        String soilType,

        IrrigationType irrigationType,

        @DecimalMin(value = "-90.0", message = "Latitude must be at least -90")
        @DecimalMax(value = "90.0", message = "Latitude must not exceed 90")
        Double latitude,

        @DecimalMin(value = "-180.0", message = "Longitude must be at least -180")
        @DecimalMax(value = "180.0", message = "Longitude must not exceed 180")
        Double longitude
) {
}
