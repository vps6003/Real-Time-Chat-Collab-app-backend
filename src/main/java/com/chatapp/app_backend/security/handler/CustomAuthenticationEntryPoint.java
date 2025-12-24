package com.chatapp.app_backend.security.handler;

import com.chatapp.app_backend.dto.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles authentication failures (401 Unauthorized)
 * Prevents Spring Security from returning HTML error pages
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex
    ) throws IOException {

        // Standardized JSON error response
        ErrorResponse error = new ErrorResponse(
                401,
                "Unauthorized",
                "e401",
                "Authentication required",
                request.getRequestURI()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Write JSON instead of HTML
        response.getWriter().write(
                objectMapper.writeValueAsString(error)
        );
    }
}
