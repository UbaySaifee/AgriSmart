package com.agrismart.backend.dto.soil;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record SoilProfileRequest(

        @Size(max = 100)
        String soilType,

        @DecimalMin(value = "0.0", message = "pH cannot be negative")
        @DecimalMax(value = "14.0", message = "pH cannot exceed 14")
        Double ph,

        @DecimalMin(value = "0.0", message = "Nitrogen cannot be negative")
        Double nitrogen,

        @DecimalMin(value = "0.0", message = "Phosphorus cannot be negative")
        Double phosphorus,

        @DecimalMin(value = "0.0", message = "Potassium cannot be negative")
        Double potassium,

        @DecimalMin(value = "0.0", message = "Organic carbon cannot be negative")
        Double organicCarbon,

        @DecimalMin(value = "0.0", message = "Electrical conductivity cannot be negative")
        Double electricalConductivity,

        @DecimalMin(value = "0.0", message = "Moisture cannot be negative")
        @DecimalMax(value = "100.0", message = "Moisture cannot exceed 100")
        Double moisturePercentage,

        @Size(max = 1000)
        String notes
) {
}