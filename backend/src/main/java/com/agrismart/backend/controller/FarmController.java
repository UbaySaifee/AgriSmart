package com.agrismart.backend.controller;

import com.agrismart.backend.dto.farm.FarmRequest;
import com.agrismart.backend.dto.farm.FarmResponse;
import com.agrismart.backend.service.FarmService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping
    public ResponseEntity<FarmResponse> createFarm(
            Authentication authentication,
            @Valid @RequestBody FarmRequest request
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        farmService.createFarm(
                                userId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getMyFarms(
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                farmService.getMyFarms(userId)
        );
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmResponse> getMyFarm(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                farmService.getMyFarm(
                        userId,
                        farmId
                )
        );
    }

    @PutMapping("/{farmId}")
    public ResponseEntity<FarmResponse> updateFarm(
            Authentication authentication,
            @PathVariable UUID farmId,
            @Valid @RequestBody FarmRequest request
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                farmService.updateFarm(
                        userId,
                        farmId,
                        request
                )
        );
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<Void> deleteFarm(
            Authentication authentication,
            @PathVariable UUID farmId
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        farmService.deleteFarm(
                userId,
                farmId
        );

        return ResponseEntity.noContent().build();
    }
}