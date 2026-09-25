package com.agrismart.backend.controller;

import com.agrismart.backend.dto.weather.WeatherResponse;
import com.agrismart.backend.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/farms/{farmId}/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(
            WeatherService weatherService
    ) {
        this.weatherService = weatherService;
    }

    @GetMapping("/latest")
    public ResponseEntity<WeatherResponse> getLatestWeather(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                weatherService.getLatestWeather(
                        userId,
                        farmId
                )
        );
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherResponse> getCurrentWeather(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(weatherService.getLatestWeather(userId, farmId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<WeatherResponse>> getWeatherHistory(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                weatherService.getWeatherHistory(
                        userId,
                        farmId
                )
        );
    }
}
