package com.chatapp.app_backend.controller;

import com.chatapp.app_backend.dto.LoginRequest;
import com.chatapp.app_backend.dto.RegisterRequest;
import com.chatapp.app_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.api.auth-base}")
public class AuthController {

    private final AuthService authService;

    //Constructor Injection ( Spring will inject AuthService)
    public AuthController (AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok("User Registered Successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        authService.login(request);
        return ResponseEntity.ok("Login successful");
    }
}
