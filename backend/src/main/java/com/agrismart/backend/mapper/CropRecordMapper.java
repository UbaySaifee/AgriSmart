package com.agrismart.backend.mapper;

import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.entity.CropRecord;
import org.springframework.stereotype.Component;

@Component
public class CropRecordMapper {

    public CropRecordResponse toResponse(
            CropRecord record
    ) {

        return new CropRecordResponse(
                record.getId(),
                record.getFarm().getId(),
                record.getCrop().getId(),
                record.getCrop().getName(),
                record.getPlantingDate(),
                record.getExpectedHarvestDate(),
                record.getActualHarvestDate(),
                record.getSeason(),
                record.getStatus(),
                record.getExpectedYieldQuintal(),
                record.getActualYieldQuintal(),
                record.getNotes(),
                record.getActive(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );
    }
}