package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.weather.WeatherResponse;
import com.agrismart.backend.entity.WeatherData;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherResponse toResponse(
            WeatherData weatherData
    ) {

        return new WeatherResponse(
                weatherData.getId(),
                weatherData.getFarm().getId(),
                weatherData.getTemperatureCelsius(),
                weatherData.getHumidityPercentage(),
                weatherData.getRainfallMm(),
                weatherData.getWindSpeedKmh(),
                weatherData.getPressureHpa(),
                weatherData.getWeatherCode(),
                weatherData.getWeatherCondition(),
                weatherData.getObservedAt(),
                weatherData.getDailyMaximumCelsius(),
                weatherData.getDailyMinimumCelsius(),
                weatherData.getDailyPrecipitationMm(),
                "LIVE"
        );
    }
}
