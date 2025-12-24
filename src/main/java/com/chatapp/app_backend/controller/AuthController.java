package com.chatapp.app_backend.controller;

import com.chatapp.app_backend.dto.auth.AuthResponse;
import com.chatapp.app_backend.dto.auth.LoginRequest;
import com.chatapp.app_backend.dto.auth.RegisterRequest;
import com.chatapp.app_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("${app.api.auth-base}")
@RestController
@RequestMapping("/vps_chat_room/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    //Constructor Injection ( Spring will inject AuthService)
    public AuthController (AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthResponse authResponse =  authService.register(request);
        return ResponseEntity.ok(authResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
