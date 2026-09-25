package com.agrismart.backend.service;

import com.agrismart.backend.dto.auth.AuthResponse;
import com.agrismart.backend.dto.auth.LoginRequest;
import com.agrismart.backend.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
