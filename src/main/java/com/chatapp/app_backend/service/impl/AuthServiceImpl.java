package com.chatapp.app_backend.service.impl;

import com.chatapp.app_backend.domain.User;
import com.chatapp.app_backend.dto.auth.AuthResponse;
import com.chatapp.app_backend.dto.auth.LoginRequest;
import com.chatapp.app_backend.dto.auth.RegisterRequest;
import com.chatapp.app_backend.exception.BadRequestException;
import com.chatapp.app_backend.exception.UnauthorizedException;
import com.chatapp.app_backend.repository.UserRepository;
import com.chatapp.app_backend.security.JwtUtil;
import com.chatapp.app_backend.service.AuthService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException(400,"Bad Request","P","Email Already Registered");
        }

        if (registerRequest.getPassword() == null ||
                registerRequest.getConfirmPassword() == null ||
                registerRequest.getPassword().isEmpty() ||
                registerRequest.getConfirmPassword().isEmpty() ||

                !registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {

            BadRequestException ex =  new BadRequestException(400,"Bad Request","P","Passwords don't match!");
            throw ex;

//            throw new BadRequestException("Confirm Password and Password don't match!");
        }


        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );

        User savedUser = userRepository.save(user);

        String token = generateToken(savedUser);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                token
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(
                        401,
                        "Unauthorized",
                        "e401-a",
                        "User Not Found in DB!"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new BadRequestException(
                    401,
                    "Unauthorized",
                    "e401-b",
                    "Invalid credentials !!"
            );
        }

        String token = generateToken(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                token
        );
    }

    private String generateToken(User user) {
        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }
}
