package com.agrismart.backend.controller;

import com.agrismart.backend.dto.soil.SoilProfileRequest;
import com.agrismart.backend.dto.soil.SoilProfileResponse;
import com.agrismart.backend.service.SoilService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/farms/{farmId}/soil")
public class SoilController {

    private final SoilService soilService;

    public SoilController(SoilService soilService) {
        this.soilService = soilService;
    }

    @PutMapping
    public ResponseEntity<SoilProfileResponse> saveSoilProfile(
            Authentication authentication,
            @PathVariable UUID farmId,
            @Valid @RequestBody SoilProfileRequest request
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                soilService.createOrUpdateSoilProfile(
                        userId,
                        farmId,
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<SoilProfileResponse> getSoilProfile(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                soilService.getSoilProfile(
                        userId,
                        farmId
                )
        );
    }
}