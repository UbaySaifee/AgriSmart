package com.agrismart.backend.controller;

import com.agrismart.backend.dto.recommendation.RecommendationResponse;
import com.agrismart.backend.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/farms/{farmId}/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<RecommendationResponse> getRecommendations(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(recommendationService.getRecommendations(userId, farmId));
    }
}
