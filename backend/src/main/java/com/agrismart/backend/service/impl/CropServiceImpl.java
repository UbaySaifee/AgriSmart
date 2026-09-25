package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.crop.CropRecordRequest;
import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.dto.crop.CropRequest;
import com.agrismart.backend.dto.crop.CropResponse;
import com.agrismart.backend.entity.Crop;
import com.agrismart.backend.entity.CropRecord;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.exception.DuplicateResourceException;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.CropMapper;
import com.agrismart.backend.mapper.CropRecordMapper;
import com.agrismart.backend.repository.CropRecordRepository;
import com.agrismart.backend.repository.CropRepository;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.service.CropService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private final CropRecordRepository cropRecordRepository;
    private final FarmRepository farmRepository;
    private final CropMapper cropMapper;
    private final CropRecordMapper cropRecordMapper;

    public CropServiceImpl(
            CropRepository cropRepository,
            CropRecordRepository cropRecordRepository,
            FarmRepository farmRepository,
            CropMapper cropMapper,
            CropRecordMapper cropRecordMapper
    ) {
        this.cropRepository = cropRepository;
        this.cropRecordRepository = cropRecordRepository;
        this.farmRepository = farmRepository;
        this.cropMapper = cropMapper;
        this.cropRecordMapper = cropRecordMapper;
    }

    @Override
    public CropResponse createCrop(CropRequest request) {

        String cropName = request.name().trim();

        if (cropRepository.existsByNameIgnoreCase(cropName)) {
            throw new DuplicateResourceException(
                    "Crop already exists: " + cropName
            );
        }

        Crop crop = new Crop();

        crop.setName(cropName);
        crop.setScientificName(request.scientificName());
        crop.setCategory(request.category());
        crop.setDescription(request.description());
        crop.setActive(true);

        Crop savedCrop = cropRepository.save(crop);

        return cropMapper.toResponse(savedCrop);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropResponse> getAllCrops() {

        return cropRepository
                .findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CropResponse getCrop(UUID cropId) {

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crop not found"
                        )
                );

        return cropMapper.toResponse(crop);
    }

    @Override
    public CropRecordResponse addCropToFarm(
            UUID userId,
            UUID farmId,
            CropRecordRequest request
    ) {

        Farm farm = findOwnedFarm(userId, farmId);

        Crop crop = cropRepository.findById(request.cropId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crop not found"
                        )
                );

        CropRecord record = new CropRecord();

        record.setFarm(farm);
        record.setCrop(crop);

        applyRequest(record, request);

        CropRecord savedRecord =
                cropRecordRepository.save(record);

        return cropRecordMapper.toResponse(savedRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropRecordResponse> getFarmCrops(
            UUID userId,
            UUID farmId
    ) {

        findOwnedFarm(userId, farmId);

        return cropRecordRepository
                .findAllByFarmIdAndActiveTrueOrderByPlantingDateDesc(farmId)
                .stream()
                .map(cropRecordMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CropRecordResponse getFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId
    ) {

        findOwnedFarm(userId, farmId);

        CropRecord record =
                findCropRecord(farmId, cropRecordId);

        return cropRecordMapper.toResponse(record);
    }

    @Override
    public CropRecordResponse updateFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId,
            CropRecordRequest request
    ) {

        findOwnedFarm(userId, farmId);

        CropRecord record =
                findCropRecord(farmId, cropRecordId);

        Crop crop = cropRepository.findById(request.cropId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crop not found"
                        )
                );

        record.setCrop(crop);

        applyRequest(record, request);

        CropRecord updatedRecord =
                cropRecordRepository.save(record);

        return cropRecordMapper.toResponse(updatedRecord);
    }

    @Override
    public void deleteFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId
    ) {

        findOwnedFarm(userId, farmId);

        CropRecord record =
                findCropRecord(farmId, cropRecordId);

        record.setActive(false);

        cropRecordRepository.save(record);
    }

    private Farm findOwnedFarm(
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

    private CropRecord findCropRecord(
            UUID farmId,
            UUID cropRecordId
    ) {

        return cropRecordRepository
                .findByIdAndFarmIdAndActiveTrue(
                        cropRecordId,
                        farmId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crop record not found"
                        )
                );
    }

    private void applyRequest(
            CropRecord record,
            CropRecordRequest request
    ) {

        record.setPlantingDate(
                request.plantingDate()
        );

        record.setExpectedHarvestDate(
                request.expectedHarvestDate()
        );

        record.setActualHarvestDate(
                request.actualHarvestDate()
        );

        record.setSeason(request.season());
        record.setStatus(request.status());

        record.setExpectedYieldQuintal(
                request.expectedYieldQuintal()
        );

        record.setActualYieldQuintal(
                request.actualYieldQuintal()
        );

        record.setNotes(request.notes());
    }
}
