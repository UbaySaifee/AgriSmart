package com.agrismart.backend.controller;

import com.agrismart.backend.dto.dashboard.DashboardResponse;
import com.agrismart.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            Authentication authentication,
            @RequestParam(required = false) UUID farmId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(dashboardService.getDashboard(userId, farmId));
    }
}
