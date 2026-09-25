package com.agrismart.backend.service;

import com.agrismart.backend.dto.recommendation.RecommendationResponse;
import com.agrismart.backend.dto.weather.WeatherResponse;

import java.util.UUID;

public interface RecommendationService {

    RecommendationResponse getRecommendations(UUID userId, UUID farmId);

    RecommendationResponse getRecommendations(UUID userId, UUID farmId, WeatherResponse weather);
}
