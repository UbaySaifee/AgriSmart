package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.farm.FarmResponse;
import com.agrismart.backend.entity.Farm;
import org.springframework.stereotype.Component;

@Component
public class FarmMapper {

    public FarmResponse toResponse(Farm farm) {

        return new FarmResponse(
                farm.getId(),
                farm.getUser().getId(),
                farm.getFarmName(),
                farm.getVillage(),
                farm.getDistrict(),
                farm.getState(),
                farm.getPincode(),
                farm.getAreaAcres(),
                farm.getSoilType(),
                farm.getIrrigationType(),
                farm.getLatitude(),
                farm.getLongitude(),
                farm.getActive(),
                farm.getCreatedAt(),
                farm.getUpdatedAt()
        );
    }
}