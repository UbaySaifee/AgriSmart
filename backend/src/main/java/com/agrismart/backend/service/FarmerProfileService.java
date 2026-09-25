package com.agrismart.backend.service;

import com.agrismart.backend.dto.farmer.FarmerProfileRequest;
import com.agrismart.backend.dto.farmer.FarmerProfileResponse;

import java.util.UUID;

public interface FarmerProfileService {

    FarmerProfileResponse createOrUpdateProfile(
            UUID userId,
            FarmerProfileRequest request
    );

    FarmerProfileResponse getMyProfile(UUID userId);
}
