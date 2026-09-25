package com.agrismart.backend.repository;

import com.agrismart.backend.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {

    List<Farm> findAllByUserIdAndActiveTrueOrderByCreatedAtDesc(UUID userId);

    Optional<Farm> findByIdAndUserIdAndActiveTrue(
            UUID farmId,
            UUID userId
    );
}
