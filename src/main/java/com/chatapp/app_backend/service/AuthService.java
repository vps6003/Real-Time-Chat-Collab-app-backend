package com.chatapp.app_backend.service;

import com.chatapp.app_backend.dto.LoginRequest;
import com.chatapp.app_backend.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void register(RegisterRequest request) {
        System.out.println("Register request received for: " + request.email);
    }

    public void login(LoginRequest request) {
        System.out.println("Login request received for: " + request.email);
    }
}
