package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.farm.FarmRequest;
import com.agrismart.backend.dto.farm.FarmResponse;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.entity.User;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.FarmMapper;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.repository.UserRepository;
import com.agrismart.backend.service.FarmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(
            FarmRepository farmRepository,
            UserRepository userRepository,
            FarmMapper farmMapper
    ) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public FarmResponse createFarm(
            UUID userId,
            FarmRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        Farm farm = new Farm();

        farm.setUser(user);

        applyRequest(farm, request);

        Farm savedFarm = farmRepository.save(farm);

        return farmMapper.toResponse(savedFarm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmResponse> getMyFarms(UUID userId) {

        return farmRepository
                .findAllByUserIdAndActiveTrueOrderByCreatedAtDesc(userId)
                .stream()
                .map(farmMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FarmResponse getMyFarm(
            UUID userId,
            UUID farmId
    ) {

        Farm farm = findOwnedFarm(userId, farmId);

        return farmMapper.toResponse(farm);
    }

    @Override
    public FarmResponse updateFarm(
            UUID userId,
            UUID farmId,
            FarmRequest request
    ) {

        Farm farm = findOwnedFarm(userId, farmId);

        applyRequest(farm, request);

        Farm updatedFarm = farmRepository.save(farm);

        return farmMapper.toResponse(updatedFarm);
    }

    @Override
    public void deleteFarm(
            UUID userId,
            UUID farmId
    ) {

        Farm farm = findOwnedFarm(userId, farmId);

        farm.setActive(false);

        farmRepository.save(farm);
    }

    private Farm findOwnedFarm(
            UUID userId,
            UUID farmId
    ) {

        return farmRepository
                .findByIdAndUserIdAndActiveTrue(farmId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Farm not found"
                        )
                );
    }

    private void applyRequest(
            Farm farm,
            FarmRequest request
    ) {

        farm.setFarmName(request.farmName().trim());
        farm.setVillage(request.village());
        farm.setDistrict(request.district());
        farm.setState(request.state());
        farm.setPincode(request.pincode());
        farm.setAreaAcres(request.areaAcres());
        farm.setSoilType(request.soilType());
        farm.setIrrigationType(request.irrigationType());
        farm.setLatitude(request.latitude());
        farm.setLongitude(request.longitude());
    }
}
