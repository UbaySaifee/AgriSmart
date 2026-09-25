package com.agrismart.backend.controller;

import com.agrismart.backend.dto.crop.CropRecordRequest;
import com.agrismart.backend.dto.crop.CropRecordResponse;
import com.agrismart.backend.dto.crop.CropRequest;
import com.agrismart.backend.dto.crop.CropResponse;
import com.agrismart.backend.service.CropService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crops")
public class CropController {

	private final CropService cropService;

	public CropController(CropService cropService) {
		this.cropService = cropService;
	}

	@PostMapping
	public ResponseEntity<CropResponse> createCrop(@Valid @RequestBody CropRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(cropService.createCrop(request));
	}

	@GetMapping
	public ResponseEntity<List<CropResponse>> getAllCrops() {

		return ResponseEntity.ok(cropService.getAllCrops());
	}

	@GetMapping("/{cropId}")
	public ResponseEntity<CropResponse> getCrop(@PathVariable UUID cropId) {

		return ResponseEntity.ok(cropService.getCrop(cropId));
	}

	@PostMapping("/farms/{farmId}")
	public ResponseEntity<CropRecordResponse> addCropToFarm(Authentication authentication, @PathVariable UUID farmId,
			@Valid @RequestBody CropRecordRequest request) {

		UUID userId = UUID.fromString(authentication.getName());

		return ResponseEntity.status(HttpStatus.CREATED).body(cropService.addCropToFarm(userId, farmId, request));
	}

	@GetMapping("/farms/{farmId}")
	public ResponseEntity<List<CropRecordResponse>> getFarmCrops(Authentication authentication,
			@PathVariable UUID farmId) {

		UUID userId = UUID.fromString(authentication.getName());

		return ResponseEntity.ok(cropService.getFarmCrops(userId, farmId));
	}

	@GetMapping("/farms/{farmId}/{cropRecordId}")
	public ResponseEntity<CropRecordResponse> getFarmCrop(Authentication authentication, @PathVariable UUID farmId,
			@PathVariable UUID cropRecordId) {

		UUID userId = UUID.fromString(authentication.getName());

		return ResponseEntity.ok(cropService.getFarmCrop(userId, farmId, cropRecordId));
	}

	@PutMapping("/farms/{farmId}/{cropRecordId}")
	public ResponseEntity<CropRecordResponse> updateFarmCrop(Authentication authentication, @PathVariable UUID farmId,
			@PathVariable UUID cropRecordId, @Valid @RequestBody CropRecordRequest request) {

		UUID userId = UUID.fromString(authentication.getName());

		return ResponseEntity.ok(cropService.updateFarmCrop(userId, farmId, cropRecordId, request));
	}

	@DeleteMapping("/farms/{farmId}/{cropRecordId}")
	public ResponseEntity<Void> deleteFarmCrop(Authentication authentication, @PathVariable UUID farmId,
			@PathVariable UUID cropRecordId) {

		UUID userId = UUID.fromString(authentication.getName());

		cropService.deleteFarmCrop(userId, farmId, cropRecordId);

		return ResponseEntity.noContent().build();
	}
}