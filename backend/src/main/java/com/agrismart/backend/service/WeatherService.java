package com.agrismart.backend.service;

import com.agrismart.backend.dto.weather.WeatherResponse;

import java.util.List;
import java.util.UUID;

public interface WeatherService {

    WeatherResponse getLatestWeather(
            UUID userId,
            UUID farmId
    );

    List<WeatherResponse> getWeatherHistory(
            UUID userId,
            UUID farmId
    );
}