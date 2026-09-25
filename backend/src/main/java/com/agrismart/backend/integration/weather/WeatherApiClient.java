package com.agrismart.backend.integration.weather;

import com.agrismart.backend.exception.ExternalServiceException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WeatherApiClient {

    private final RestClient restClient;

    public WeatherApiClient(
            @Value("${app.weather.base-url}") String baseUrl,
            @Value("${app.weather.connect-timeout-ms}") long connectTimeoutMs,
            @Value("${app.weather.read-timeout-ms}") long readTimeoutMs
    ) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(connectTimeoutMs))
                .build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
    }

    public WeatherDataDto getCurrentWeather(
            double latitude,
            double longitude
    ) {

        try {
            OpenMeteoResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m,surface_pressure")
                            .queryParam("daily", "temperature_2m_max,temperature_2m_min,precipitation_sum")
                            .queryParam("forecast_days", 1)
                            .queryParam("timezone", "auto")
                            .build())
                    .retrieve()
                    .body(OpenMeteoResponse.class);

            if (response == null || response.current() == null) {
                throw new ExternalServiceException("Live weather is temporarily unavailable");
            }

            CurrentWeather current = response.current();
            DailyWeather daily = response.daily();

            return new WeatherDataDto(
                    current.temperatureCelsius(),
                    current.humidityPercentage(),
                    current.precipitationMm(),
                    current.windSpeedKmh(),
                    current.pressureHpa(),
                    current.weatherCode(),
                    describeWeatherCode(current.weatherCode()),
                    current.observedAt(),
                    first(daily == null ? null : daily.maximumTemperatureCelsius()),
                    first(daily == null ? null : daily.minimumTemperatureCelsius()),
                    first(daily == null ? null : daily.precipitationSumMm())
            );
        } catch (ExternalServiceException exception) {
            throw exception;
        } catch (RestClientException | IllegalArgumentException exception) {
            throw new ExternalServiceException(
                    "Live weather is temporarily unavailable. Please try again shortly.",
                    exception
            );
        }
    }

    public record WeatherDataDto(
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
            Double dailyPrecipitationMm
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OpenMeteoResponse(CurrentWeather current, DailyWeather daily) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record CurrentWeather(
            @JsonProperty("temperature_2m") Double temperatureCelsius,
            @JsonProperty("relative_humidity_2m") Double humidityPercentage,
            @JsonProperty("precipitation") Double precipitationMm,
            @JsonProperty("weather_code") Integer weatherCode,
            @JsonProperty("wind_speed_10m") Double windSpeedKmh,
            @JsonProperty("surface_pressure") Double pressureHpa,
            @JsonProperty("time") LocalDateTime observedAt
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DailyWeather(
            @JsonProperty("temperature_2m_max") List<Double> maximumTemperatureCelsius,
            @JsonProperty("temperature_2m_min") List<Double> minimumTemperatureCelsius,
            @JsonProperty("precipitation_sum") List<Double> precipitationSumMm
    ) {
    }

    private Double first(List<Double> values) {
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    private String describeWeatherCode(Integer code) {
        if (code == null) return "Unknown";
        return switch (code) {
            case 0 -> "Clear sky";
            case 1 -> "Mainly clear";
            case 2 -> "Partly cloudy";
            case 3 -> "Overcast";
            case 45, 48 -> "Foggy";
            case 51, 53, 55, 56, 57 -> "Drizzle";
            case 61, 63, 65, 66, 67 -> "Rain";
            case 71, 73, 75, 77 -> "Snow";
            case 80, 81, 82 -> "Rain showers";
            case 85, 86 -> "Snow showers";
            case 95, 96, 99 -> "Thunderstorm";
            default -> "Variable conditions";
        };
    }
}
