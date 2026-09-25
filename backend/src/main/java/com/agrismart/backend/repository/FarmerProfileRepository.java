package com.agrismart.backend.repository;

import com.agrismart.backend.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FarmerProfileRepository
        extends JpaRepository<FarmerProfile, UUID> {

    Optional<FarmerProfile> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
