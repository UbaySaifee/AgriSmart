package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.crop.CropResponse;
import com.agrismart.backend.entity.Crop;
import org.springframework.stereotype.Component;

@Component
public class CropMapper {

    public CropResponse toResponse(Crop crop) {

        return new CropResponse(
                crop.getId(),
                crop.getName(),
                crop.getScientificName(),
                crop.getCategory(),
                crop.getDescription(),
                crop.getActive(),
                crop.getCreatedAt(),
                crop.getUpdatedAt()
        );
    }
}