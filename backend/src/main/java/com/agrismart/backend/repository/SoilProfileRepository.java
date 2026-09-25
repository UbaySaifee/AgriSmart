package com.agrismart.backend.repository;

import com.agrismart.backend.entity.SoilProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SoilProfileRepository
        extends JpaRepository<SoilProfile, UUID> {

    Optional<SoilProfile> findByFarmId(UUID farmId);

    Optional<SoilProfile> findByIdAndFarmId(
            UUID soilProfileId,
            UUID farmId
    );
}