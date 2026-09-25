package com.agrismart.backend.service;

import com.agrismart.backend.dto.soil.SoilProfileRequest;
import com.agrismart.backend.dto.soil.SoilProfileResponse;

import java.util.UUID;

public interface SoilService {

    SoilProfileResponse createOrUpdateSoilProfile(
            UUID userId,
            UUID farmId,
            SoilProfileRequest request
    );

    SoilProfileResponse getSoilProfile(
            UUID userId,
            UUID farmId
    );
}