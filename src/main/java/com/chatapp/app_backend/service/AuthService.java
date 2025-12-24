package com.chatapp.app_backend.service;

import com.chatapp.app_backend.dto.auth.AuthResponse;
import com.chatapp.app_backend.dto.auth.LoginRequest;
import com.chatapp.app_backend.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register (RegisterRequest registerRequest);

    AuthResponse login (LoginRequest loginRequest);
}

