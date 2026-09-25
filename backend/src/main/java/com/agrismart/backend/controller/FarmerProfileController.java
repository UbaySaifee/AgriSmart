package com.agrismart.backend.controller;

import com.agrismart.backend.dto.farmer.FarmerProfileRequest;
import com.agrismart.backend.dto.farmer.FarmerProfileResponse;
import com.agrismart.backend.service.FarmerProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/farmer/profile")
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;

    public FarmerProfileController(
            FarmerProfileService farmerProfileService
    ) {
        this.farmerProfileService = farmerProfileService;
    }

    @GetMapping
    public ResponseEntity<FarmerProfileResponse> getMyProfile(
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                farmerProfileService.getMyProfile(userId)
        );
    }

    @PutMapping
    public ResponseEntity<FarmerProfileResponse> saveOrUpdateProfile(
            Authentication authentication,
            @Valid @RequestBody FarmerProfileRequest request
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                farmerProfileService.createOrUpdateProfile(
                        userId,
                        request
                )
        );
    }
}
