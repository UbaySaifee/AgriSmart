package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.dto.dashboard.DashboardResponse;
import com.agrismart.backend.dto.farmer.FarmerProfileResponse;
import com.agrismart.backend.dto.soil.SoilProfileResponse;
import com.agrismart.backend.dto.weather.WeatherResponse;
import com.agrismart.backend.entity.Farm;
import com.agrismart.backend.exception.ExternalServiceException;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.CropRecordMapper;
import com.agrismart.backend.mapper.FarmMapper;
import com.agrismart.backend.mapper.FarmerProfileMapper;
import com.agrismart.backend.mapper.SoilProfileMapper;
import com.agrismart.backend.repository.CropRecordRepository;
import com.agrismart.backend.repository.FarmRepository;
import com.agrismart.backend.repository.FarmerProfileRepository;
import com.agrismart.backend.repository.SoilProfileRepository;
import com.agrismart.backend.service.DashboardService;
import com.agrismart.backend.service.RecommendationService;
import com.agrismart.backend.service.UserService;
import com.agrismart.backend.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final FarmRepository farmRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final SoilProfileRepository soilProfileRepository;
    private final CropRecordRepository cropRecordRepository;
    private final UserService userService;
    private final WeatherService weatherService;
    private final RecommendationService recommendationService;
    private final FarmMapper farmMapper;
    private final FarmerProfileMapper farmerProfileMapper;
    private final SoilProfileMapper soilProfileMapper;
    private final CropRecordMapper cropRecordMapper;

    public DashboardServiceImpl(
            FarmRepository farmRepository,
            FarmerProfileRepository farmerProfileRepository,
            SoilProfileRepository soilProfileRepository,
            CropRecordRepository cropRecordRepository,
            UserService userService,
            WeatherService weatherService,
            RecommendationService recommendationService,
            FarmMapper farmMapper,
            FarmerProfileMapper farmerProfileMapper,
            SoilProfileMapper soilProfileMapper,
            CropRecordMapper cropRecordMapper
    ) {
        this.farmRepository = farmRepository;
        this.farmerProfileRepository = farmerProfileRepository;
        this.soilProfileRepository = soilProfileRepository;
        this.cropRecordRepository = cropRecordRepository;
        this.userService = userService;
        this.weatherService = weatherService;
        this.recommendationService = recommendationService;
        this.farmMapper = farmMapper;
        this.farmerProfileMapper = farmerProfileMapper;
        this.soilProfileMapper = soilProfileMapper;
        this.cropRecordMapper = cropRecordMapper;
    }

    @Override
    public DashboardResponse getDashboard(UUID userId, UUID requestedFarmId) {
        Farm farm = requestedFarmId == null
                ? farmRepository.findAllByUserIdAndActiveTrueOrderByCreatedAtDesc(userId)
                    .stream().findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Create a farm to view the dashboard"))
                : farmRepository.findByIdAndUserIdAndActiveTrue(requestedFarmId, userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));

        FarmerProfileResponse profile = farmerProfileRepository.findByUserId(userId)
                .map(farmerProfileMapper::toResponse)
                .orElse(null);
        SoilProfileResponse soil = soilProfileRepository.findByFarmId(farm.getId())
                .map(soilProfileMapper::toResponse)
                .orElse(null);
        CropRecordResponse crop = cropRecordRepository
                .findFirstByFarmIdAndActiveTrueOrderByPlantingDateDesc(farm.getId())
                .map(cropRecordMapper::toResponse)
                .orElse(null);

        WeatherResponse weather = null;
        String weatherMessage = null;
        try {
            weather = weatherService.getLatestWeather(userId, farm.getId());
        } catch (ExternalServiceException exception) {
            weatherMessage = exception.getMessage();
        }

        return new DashboardResponse(
                userService.getCurrentUser(userId),
                profile,
                farmMapper.toResponse(farm),
                soil,
                crop,
                weather,
                weatherMessage,
                recommendationService.getRecommendations(userId, farm.getId(), weather)
        );
    }
}
