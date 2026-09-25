package com.agrismart.backend.dto.weather;

import java.time.LocalDateTime;
import java.util.UUID;

public record WeatherResponse(
        UUID id,
        UUID farmId,
        Double temperatureCelsius,
        Double humidityPercentage,
        Double rainfallMm,
        Double windSpeedKmh,
        Double pressureHpa,
        Integer weatherCode,
        String weatherCondition,
        LocalDateTime observedAt,
        Double dailyMaximumCelsius,
        Double dailyMinimumCelsius,
        Double dailyPrecipitationMm,
        String source
) {
}
