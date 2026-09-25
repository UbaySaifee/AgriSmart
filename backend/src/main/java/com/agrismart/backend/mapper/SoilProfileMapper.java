package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.soil.SoilProfileResponse;
import com.agrismart.backend.entity.SoilProfile;
import org.springframework.stereotype.Component;

@Component
public class SoilProfileMapper {

    public SoilProfileResponse toResponse(
            SoilProfile soilProfile
    ) {

        return new SoilProfileResponse(
                soilProfile.getId(),
                soilProfile.getFarm().getId(),
                soilProfile.getSoilType(),
                soilProfile.getPh(),
                soilProfile.getNitrogen(),
                soilProfile.getPhosphorus(),
                soilProfile.getPotassium(),
                soilProfile.getOrganicCarbon(),
                soilProfile.getElectricalConductivity(),
                soilProfile.getMoisturePercentage(),
                soilProfile.getNotes(),
                soilProfile.getActive(),
                soilProfile.getCreatedAt(),
                soilProfile.getUpdatedAt()
        );
    }
}