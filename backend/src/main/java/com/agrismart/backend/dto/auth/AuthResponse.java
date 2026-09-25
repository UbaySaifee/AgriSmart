package com.agrismart.backend.dto.auth;

import com.agrismart.backend.entity.Role;

import java.util.UUID;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UUID userId,
        String fullName,
        String email,
        Role role
) {
}
