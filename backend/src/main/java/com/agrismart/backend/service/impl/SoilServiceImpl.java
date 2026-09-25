package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.soil.SoilProfileRequest;
import com.agrismart.backend.dto.soil.SoilProfileResponse;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.entity.SoilProfile;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.SoilProfileMapper;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.repository.SoilProfileRepository;
import com.agrismart.backend.service.SoilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SoilServiceImpl implements SoilService {

    private final SoilProfileRepository soilProfileRepository;
    private final FarmRepository farmRepository;
    private final SoilProfileMapper soilProfileMapper;

    public SoilServiceImpl(
            SoilProfileRepository soilProfileRepository,
            FarmRepository farmRepository,
            SoilProfileMapper soilProfileMapper
    ) {
        this.soilProfileRepository = soilProfileRepository;
        this.farmRepository = farmRepository;
        this.soilProfileMapper = soilProfileMapper;
    }

    @Override
    public SoilProfileResponse createOrUpdateSoilProfile(
            UUID userId,
            UUID farmId,
            SoilProfileRequest request
    ) {

        Farm farm = farmRepository
                .findByIdAndUserIdAndActiveTrue(farmId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Farm not found"
                        )
                );

        SoilProfile soilProfile =
                soilProfileRepository
                        .findByFarmId(farmId)
                        .orElseGet(SoilProfile::new);

        soilProfile.setFarm(farm);
        soilProfile.setSoilType(request.soilType());
        soilProfile.setPh(request.ph());
        soilProfile.setNitrogen(request.nitrogen());
        soilProfile.setPhosphorus(request.phosphorus());
        soilProfile.setPotassium(request.potassium());
        soilProfile.setOrganicCarbon(request.organicCarbon());
        soilProfile.setElectricalConductivity(
                request.electricalConductivity()
        );
        soilProfile.setMoisturePercentage(
                request.moisturePercentage()
        );
        soilProfile.setNotes(request.notes());
        soilProfile.setActive(true);

        SoilProfile savedProfile =
                soilProfileRepository.save(soilProfile);

        return soilProfileMapper.toResponse(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public SoilProfileResponse getSoilProfile(
            UUID userId,
            UUID farmId
    ) {

        farmRepository
                .findByIdAndUserIdAndActiveTrue(farmId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Farm not found"
                        )
                );

        SoilProfile soilProfile =
                soilProfileRepository
                        .findByFarmId(farmId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Soil profile not found"
                                )
                        );

        return soilProfileMapper.toResponse(soilProfile);
    }
}
