package com.agrismart.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "weather_data",
        indexes = {
                @Index(
                        name = "idx_weather_farm_id",
                        columnList = "farm_id"
                ),
                @Index(
                        name = "idx_weather_observed_at",
                        columnList = "observed_at"
                )
        }
)
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "farm_id",
            nullable = false
    )
    private Farm farm;

    private Double temperatureCelsius;

    private Double humidityPercentage;

    private Double rainfallMm;

    private Double windSpeedKmh;

    private Double pressureHpa;

    @Column(length = 100)
    private String weatherCondition;

    private Integer weatherCode;

    private Double dailyMaximumCelsius;

    private Double dailyMinimumCelsius;

    private Double dailyPrecipitationMm;

    private LocalDateTime observedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (observedAt == null) {
            observedAt = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(Double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public Double getHumidityPercentage() {
        return humidityPercentage;
    }

    public void setHumidityPercentage(Double humidityPercentage) {
        this.humidityPercentage = humidityPercentage;
    }

    public Double getRainfallMm() {
        return rainfallMm;
    }

    public void setRainfallMm(Double rainfallMm) {
        this.rainfallMm = rainfallMm;
    }

    public Double getWindSpeedKmh() {
        return windSpeedKmh;
    }

    public void setWindSpeedKmh(Double windSpeedKmh) {
        this.windSpeedKmh = windSpeedKmh;
    }

    public Double getPressureHpa() {
        return pressureHpa;
    }

    public void setPressureHpa(Double pressureHpa) {
        this.pressureHpa = pressureHpa;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public Integer getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(Integer weatherCode) {
        this.weatherCode = weatherCode;
    }

    public Double getDailyMaximumCelsius() {
        return dailyMaximumCelsius;
    }

    public void setDailyMaximumCelsius(Double dailyMaximumCelsius) {
        this.dailyMaximumCelsius = dailyMaximumCelsius;
    }

    public Double getDailyMinimumCelsius() {
        return dailyMinimumCelsius;
    }

    public void setDailyMinimumCelsius(Double dailyMinimumCelsius) {
        this.dailyMinimumCelsius = dailyMinimumCelsius;
    }

    public Double getDailyPrecipitationMm() {
        return dailyPrecipitationMm;
    }

    public void setDailyPrecipitationMm(Double dailyPrecipitationMm) {
        this.dailyPrecipitationMm = dailyPrecipitationMm;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
