package com.agrismart.backend.repository;

import com.agrismart.backend.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeatherDataRepository
        extends JpaRepository<WeatherData, UUID> {

    List<WeatherData> findTop24ByFarmIdOrderByObservedAtDesc(
            UUID farmId
    );

    Optional<WeatherData> findTopByFarmIdOrderByObservedAtDesc(
            UUID farmId
    );
}