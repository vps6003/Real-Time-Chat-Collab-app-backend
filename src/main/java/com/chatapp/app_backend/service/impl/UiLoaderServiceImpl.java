package com.chatapp.app_backend.service.impl;

import com.chatapp.app_backend.domain.User;
;
import com.chatapp.app_backend.dto.uiloader.UiLoaderResponse;
import com.chatapp.app_backend.exception.NotFoundException;
import com.chatapp.app_backend.repository.UserRepository;

import com.chatapp.app_backend.service.UiLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UiLoaderServiceImpl implements UiLoaderService {

    private final UserRepository userRepository;

    @Override
    public UiLoaderResponse load(String email) {
        // Fetch user from DB using the email extracted from the JWT
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        401,
                        "Token Invalid",
                        "T-401",
                        "User session not found"
                        )
                );

        // Map the DB entity to our Response DTO
        return UiLoaderResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}