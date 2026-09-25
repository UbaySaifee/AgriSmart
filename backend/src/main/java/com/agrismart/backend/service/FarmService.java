package com.agrismart.backend.service;

import com.agrismart.backend.dto.farm.FarmRequest;
import com.agrismart.backend.dto.farm.FarmResponse;

import java.util.List;
import java.util.UUID;

public interface FarmService {

    FarmResponse createFarm(
            UUID userId,
            FarmRequest request
    );

    List<FarmResponse> getMyFarms(UUID userId);

    FarmResponse getMyFarm(
            UUID userId,
            UUID farmId
    );

    FarmResponse updateFarm(
            UUID userId,
            UUID farmId,
            FarmRequest request
    );

    void deleteFarm(
            UUID userId,
            UUID farmId
    );
}