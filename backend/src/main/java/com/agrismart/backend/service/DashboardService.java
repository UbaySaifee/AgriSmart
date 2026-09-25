package com.agrismart.backend.service;

import com.agrismart.backend.dto.dashboard.DashboardResponse;

import java.util.UUID;

public interface DashboardService {

    DashboardResponse getDashboard(UUID userId, UUID farmId);
}
