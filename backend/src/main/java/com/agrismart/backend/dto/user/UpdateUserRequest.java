package com.agrismart.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "Full name is required")
        @Size(
                min = 2,
                max = 120,
                message = "Full name must be between 2 and 120 characters"
        )
        String fullName,

        @Size(
                max = 20,
                message = "Phone number cannot exceed 20 characters"
        )
        String phone
) {
}
