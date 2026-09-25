package com.agrismart.backend.service;

import com.agrismart.backend.dto.crop.CropRecordRequest;
import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.dto.crop.CropRequest;
import com.agrismart.backend.dto.crop.CropResponse;

import java.util.List;
import java.util.UUID;

public interface CropService {

    CropResponse createCrop(CropRequest request);

    List<CropResponse> getAllCrops();

    CropResponse getCrop(UUID cropId);

    CropRecordResponse addCropToFarm(
            UUID userId,
            UUID farmId,
            CropRecordRequest request
    );

    List<CropRecordResponse> getFarmCrops(
            UUID userId,
            UUID farmId
    );

    CropRecordResponse getFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId
    );

    CropRecordResponse updateFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId,
            CropRecordRequest request
    );

    void deleteFarmCrop(
            UUID userId,
            UUID farmId,
            UUID cropRecordId
    );
}