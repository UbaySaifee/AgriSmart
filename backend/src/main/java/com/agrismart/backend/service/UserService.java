package com.agrismart.backend.service;

import com.agrismart.backend.dto.user.UpdateUserRequest;
import com.agrismart.backend.dto.user.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse getCurrentUser(UUID userId);

    UserResponse getUserById(UUID userId);

    UserResponse updateCurrentUser(
            UUID userId,
            UpdateUserRequest request
    );
}
