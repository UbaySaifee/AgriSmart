package com.agrismart.backend.dto.dashboard;

import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.dto.farm.FarmResponse;
import com.agrismart.backend.dto.farmer.FarmerProfileResponse;
import com.agrismart.backend.dto.recommendation.RecommendationResponse;
import com.agrismart.backend.dto.soil.SoilProfileResponse;
import com.agrismart.backend.dto.user.UserResponse;
import com.agrismart.backend.dto.weather.WeatherResponse;

public record DashboardResponse(
        UserResponse farmer,
        FarmerProfileResponse profile,
        FarmResponse selectedFarm,
        SoilProfileResponse soil,
        CropRecordResponse currentCrop,
        WeatherResponse weather,
        String weatherMessage,
        RecommendationResponse advisory
) {
}
