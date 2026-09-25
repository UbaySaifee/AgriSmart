package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.farmer.FarmerProfileResponse;
import com.agrismart.backend.entity.FarmerProfile;
import org.springframework.stereotype.Component;

@Component
public class FarmerProfileMapper {

    public FarmerProfileResponse toResponse(
            FarmerProfile profile
    ) {

        return new FarmerProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getVillage(),
                profile.getDistrict(),
                profile.getState(),
                profile.getPincode(),
                profile.getFarmingType(),
                profile.getIrrigationType(),
                profile.getTotalLandAcres(),
                profile.getFarmingExperienceYears(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
