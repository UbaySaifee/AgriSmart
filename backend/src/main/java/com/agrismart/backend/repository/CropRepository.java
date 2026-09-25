package com.agrismart.backend.repository;

import com.agrismart.backend.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CropRepository extends JpaRepository<Crop, UUID> {

    Optional<Crop> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Crop> findAllByActiveTrueOrderByNameAsc();
}