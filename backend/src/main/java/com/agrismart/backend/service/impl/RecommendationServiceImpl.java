package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.recommendation.RecommendationItem;
import com.agrismart.backend.dto.recommendation.RecommendationResponse;
import com.agrismart.backend.dto.weather.WeatherResponse;
import com.agrismart.backend.entity.CropRecord;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.entity.SoilProfile;
import com.agrismart.backend.exception.ExternalServiceException;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.repository.CropRecordRepository;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.repository.SoilProfileRepository;
import com.agrismart.backend.service.RecommendationService;
import com.agrismart.backend.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    public static final String PROTOTYPE_NOTICE =
            "Prototype advisory — verify critical farm decisions with local agricultural experts.";

    private final FarmRepository farmRepository;
    private final SoilProfileRepository soilProfileRepository;
    private final CropRecordRepository cropRecordRepository;
    private final WeatherService weatherService;

    public RecommendationServiceImpl(
            FarmRepository farmRepository,
            SoilProfileRepository soilProfileRepository,
            CropRecordRepository cropRecordRepository,
            WeatherService weatherService
    ) {
        this.farmRepository = farmRepository;
        this.soilProfileRepository = soilProfileRepository;
        this.cropRecordRepository = cropRecordRepository;
        this.weatherService = weatherService;
    }

    @Override
    public RecommendationResponse getRecommendations(UUID userId, UUID farmId) {
        WeatherResponse weather = null;
        try {
            weather = weatherService.getLatestWeather(userId, farmId);
        } catch (ExternalServiceException ignored) {
            // Soil and crop rules remain useful when the external provider is unavailable.
        }
        return getRecommendations(userId, farmId, weather);
    }

    @Override
    public RecommendationResponse getRecommendations(
            UUID userId,
            UUID farmId,
            WeatherResponse weather
    ) {
        Farm farm = farmRepository.findByIdAndUserIdAndActiveTrue(farmId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
        SoilProfile soil = soilProfileRepository.findByFarmId(farmId).orElse(null);
        CropRecord crop = cropRecordRepository
                .findFirstByFarmIdAndActiveTrueOrderByPlantingDateDesc(farmId)
                .orElse(null);

        Instant generatedAt = Instant.now();
        List<RecommendationItem> items = new ArrayList<>();

        // Prototype/demo heuristics only. These are intentionally broad signals,
        // not universal agronomic thresholds or certified prescriptions.
        if (weather != null && value(weather.dailyPrecipitationMm()) >= 2.0) {
            items.add(item("rain-delay", "WEATHER", "MEDIUM",
                    "Rain may support the field today",
                    "Consider checking field conditions before starting irrigation.",
                    String.format("Today's forecast indicates approximately %.1f mm of precipitation.", value(weather.dailyPrecipitationMm())),
                    generatedAt));
        } else if (soil != null && value(soil.getMoisturePercentage()) < 30.0) {
            items.add(item("low-moisture", "IRRIGATION", "HIGH",
                    "Irrigation attention required",
                    "Inspect the root zone and consider irrigation if the crop shows water stress.",
                    "Recorded soil moisture is below the prototype 30% attention threshold and meaningful rain is not indicated.",
                    generatedAt));
        } else if (soil != null) {
            items.add(item("moisture-stable", "IRRIGATION", "LOW",
                    "Moisture level looks stable",
                    "Continue monitoring before the next irrigation cycle.",
                    String.format("Recorded soil moisture is %.1f%%.", value(soil.getMoisturePercentage())),
                    generatedAt));
        }

        if (weather != null && value(weather.temperatureCelsius()) >= 35.0) {
            items.add(item("heat-attention", "WEATHER", "HIGH",
                    "Heat stress attention",
                    "Avoid midday field operations and watch the crop for heat stress.",
                    String.format("Current temperature is %.1f°C, above the prototype 35°C heat-attention threshold.", value(weather.temperatureCelsius())),
                    generatedAt));
        } else if (weather == null) {
            items.add(item("weather-unavailable", "WEATHER", "INFO",
                    "Weather check unavailable",
                    "Retry shortly; soil and crop observations are still shown.",
                    "The live weather provider could not be reached or farm coordinates are missing.",
                    generatedAt));
        }

        if (soil == null) {
            items.add(item("soil-missing", "SOIL", "MEDIUM",
                    "Add a soil profile",
                    "Record pH and nutrient readings to unlock more useful advisories.",
                    "No soil profile is available for this farm.",
                    generatedAt));
        } else if (soil.getPh() != null && (soil.getPh() < 5.5 || soil.getPh() > 8.0)) {
            items.add(item("ph-attention", "SOIL", "MEDIUM",
                    "Soil pH needs attention",
                    "Consider confirming the reading with a soil test and seek local guidance before treatment.",
                    String.format("Recorded pH is %.1f, outside the prototype 5.5–8.0 observation range.", soil.getPh()),
                    generatedAt));
        } else if (soil.getPh() != null) {
            items.add(item("ph-range", "SOIL", "INFO",
                    "Soil pH is within the demo range",
                    "Keep seasonal soil tests in your farm routine.",
                    String.format("Recorded pH is %.1f.", soil.getPh()),
                    generatedAt));
        }

        if (crop == null) {
            items.add(item("crop-missing", "CROP", "MEDIUM",
                    "Add the current crop",
                    "Record the crop and sowing date for crop-aware observations.",
                    "No active crop record is linked to this farm.",
                    generatedAt));
        } else {
            items.add(item("crop-monitor-" + crop.getId(), "CROP", "INFO",
                    crop.getCrop().getName() + " crop overview",
                    "Keep field notes updated as the crop progresses through the season.",
                    "The current farm record is marked " + safe(crop.getStatus(), "active") + " for the " + safe(crop.getSeason(), "current") + " season.",
                    generatedAt));
        }

        if (items.size() < 3) {
            items.add(item("farm-routine", "GENERAL", "INFO",
                    "Keep farm records current",
                    "Update soil moisture and crop status after major field activity.",
                    "Fresh observations make future prototype advisories more relevant for " + farm.getFarmName() + ".",
                    generatedAt));
        }

        return new RecommendationResponse(farmId, PROTOTYPE_NOTICE, items.stream().limit(5).toList());
    }

    private RecommendationItem item(
            String id, String category, String severity, String title,
            String message, String reason, Instant generatedAt
    ) {
        return new RecommendationItem(id, category, severity, title, message, reason, generatedAt);
    }

    private double value(Double number) {
        return number == null ? 0.0 : number;
    }

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.toLowerCase();
    }
}
