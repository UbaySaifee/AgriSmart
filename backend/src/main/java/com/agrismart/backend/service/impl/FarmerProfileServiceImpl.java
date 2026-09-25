package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.farmer.FarmerProfileRequest;
import com.agrismart.backend.dto.farmer.FarmerProfileResponse;
import com.agrismart.backend.entity.FarmerProfile;
import com.agrismart.backend.entity.User;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.FarmerProfileMapper;
import com.agrismart.backend.repository.FarmerProfileRepository;
import com.agrismart.backend.repository.UserRepository;
import com.agrismart.backend.service.FarmerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FarmerProfileServiceImpl
        implements FarmerProfileService {

    private final FarmerProfileRepository farmerProfileRepository;
    private final UserRepository userRepository;
    private final FarmerProfileMapper farmerProfileMapper;

    public FarmerProfileServiceImpl(
            FarmerProfileRepository farmerProfileRepository,
            UserRepository userRepository,
            FarmerProfileMapper farmerProfileMapper
    ) {
        this.farmerProfileRepository = farmerProfileRepository;
        this.userRepository = userRepository;
        this.farmerProfileMapper = farmerProfileMapper;
    }

    @Override
    public FarmerProfileResponse createOrUpdateProfile(
            UUID userId,
            FarmerProfileRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        FarmerProfile profile =
                farmerProfileRepository.findByUserId(userId)
                        .orElseGet(FarmerProfile::new);

        profile.setUser(user);
        profile.setVillage(request.village());
        profile.setDistrict(request.district());
        profile.setState(request.state());
        profile.setPincode(request.pincode());
        profile.setFarmingType(request.farmingType());
        profile.setIrrigationType(request.irrigationType());
        profile.setTotalLandAcres(request.totalLandAcres());
        profile.setFarmingExperienceYears(
                request.farmingExperienceYears()
        );

        FarmerProfile savedProfile =
                farmerProfileRepository.save(profile);

        return farmerProfileMapper.toResponse(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerProfileResponse getMyProfile(UUID userId) {

        FarmerProfile profile =
                farmerProfileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Farmer profile not found"
                                )
                        );

        return farmerProfileMapper.toResponse(profile);
    }
}
