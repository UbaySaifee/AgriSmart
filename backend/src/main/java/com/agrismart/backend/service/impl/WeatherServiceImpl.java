package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.weather.WeatherResponse;
import com.agrismart.backend.entity.WeatherData;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.exception.ExternalServiceException;
import com.agrismart.backend.integration.weather.WeatherApiClient;
import com.agrismart.backend.mapper.WeatherMapper;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.repository.WeatherDataRepository;
import com.agrismart.backend.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(noRollbackFor = ExternalServiceException.class)
public class WeatherServiceImpl implements WeatherService {

    private final WeatherDataRepository weatherDataRepository;
    private final FarmRepository farmRepository;
    private final WeatherMapper weatherMapper;
    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(
            WeatherDataRepository weatherDataRepository,
            FarmRepository farmRepository,
            WeatherMapper weatherMapper,
            WeatherApiClient weatherApiClient
    ) {
        this.weatherDataRepository = weatherDataRepository;
        this.farmRepository = farmRepository;
        this.weatherMapper = weatherMapper;
        this.weatherApiClient = weatherApiClient;
    }

    @Override
    public WeatherResponse getLatestWeather(
            UUID userId,
            UUID farmId
    ) {

        Farm farm = verifyFarmOwnership(userId, farmId);
        if (farm.getLatitude() == null || farm.getLongitude() == null) {
            throw new ExternalServiceException(
                    "Add valid latitude and longitude to this farm to view live weather"
            );
        }

        WeatherApiClient.WeatherDataDto live = weatherApiClient.getCurrentWeather(
                farm.getLatitude(), farm.getLongitude()
        );

        WeatherData weatherData = new WeatherData();
        weatherData.setFarm(farm);
        weatherData.setTemperatureCelsius(live.temperatureCelsius());
        weatherData.setHumidityPercentage(live.humidityPercentage());
        weatherData.setRainfallMm(live.rainfallMm());
        weatherData.setWindSpeedKmh(live.windSpeedKmh());
        weatherData.setPressureHpa(live.pressureHpa());
        weatherData.setWeatherCode(live.weatherCode());
        weatherData.setWeatherCondition(live.weatherCondition());
        weatherData.setObservedAt(live.observedAt());
        weatherData.setDailyMaximumCelsius(live.dailyMaximumCelsius());
        weatherData.setDailyMinimumCelsius(live.dailyMinimumCelsius());
        weatherData.setDailyPrecipitationMm(live.dailyPrecipitationMm());
        weatherData = weatherDataRepository.save(weatherData);

        return weatherMapper.toResponse(weatherData);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeatherResponse> getWeatherHistory(
            UUID userId,
            UUID farmId
    ) {

        verifyFarmOwnership(userId, farmId);

        return weatherDataRepository
                .findTop24ByFarmIdOrderByObservedAtDesc(farmId)
                .stream()
                .map(weatherMapper::toResponse)
                .toList();
    }

    private Farm verifyFarmOwnership(
            UUID userId,
            UUID farmId
    ) {

        return farmRepository
                .findByIdAndUserIdAndActiveTrue(farmId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Farm not found"
                        )
                );
    }
}
