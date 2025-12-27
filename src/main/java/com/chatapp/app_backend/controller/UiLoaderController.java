package com.chatapp.app_backend.controller;


import com.chatapp.app_backend.dto.uiloader.UiLoaderResponse;
import com.chatapp.app_backend.exception.BadRequestException;
import com.chatapp.app_backend.service.UiLoaderService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${app.api.base}")
@RequiredArgsConstructor
public class UiLoaderController {

    private final UiLoaderService uiLoaderService;

    /**
     * Endpoint: GET /api/users/me
     * Purpose: Validates the JWT in the header and returns user details.
     */
    @GetMapping("/loader")
    public ResponseEntity<UiLoaderResponse> validateSession(
            @RequestParam String email,
            Authentication authentication) {
        // 'authentication' is populated by your JwtFilter.
        // If the code reaches here, the token is VALID.
        Map<String,String> credentials = (Map<String, String>) authentication.getCredentials();

        if(!email.equals(credentials.get("email"))){
            throw new BadRequestException(
                    401,
                    "S-401",
                    "Invalid Session",
                    "Email not found for current session!!"
            );
        }
        UiLoaderResponse response = uiLoaderService.load(email);
        return ResponseEntity.ok(response);
    }

}