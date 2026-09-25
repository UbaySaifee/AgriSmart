package com.agrismart.backend.repository;

import com.agrismart.backend.entity.CropRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CropRecordRepository
        extends JpaRepository<CropRecord, UUID> {

    List<CropRecord> findAllByFarmIdAndActiveTrueOrderByPlantingDateDesc(
            UUID farmId
    );

    Optional<CropRecord> findByIdAndFarmIdAndActiveTrue(
            UUID cropRecordId,
            UUID farmId
    );

    Optional<CropRecord> findFirstByFarmIdAndActiveTrueOrderByPlantingDateDesc(UUID farmId);
}
