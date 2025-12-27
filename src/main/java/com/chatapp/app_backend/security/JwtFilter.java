package com.chatapp.app_backend.security;

import com.chatapp.app_backend.config.AppProperties;
import com.chatapp.app_backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT authentication filter
 * Runs once per request
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Base auth path injected from properties
    // Used to SKIP JWT validation for login/register
//    @Value("${app.api.auth-base}")
//    private String authBasePath;

    private final AppProperties appProperties;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository,
                     AppProperties appProperties) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.appProperties =appProperties;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Skip JWT processing for public auth endpoints
        if (path.startsWith(appProperties.getApi().getAuthBase())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // Validate Bearer token if present
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                // Extract claims from token
                Claims claims = jwtUtil.extractClaims(token);
                String email = claims.get("email", String.class);

                // Load user and set authentication context
                userRepository.findByEmail(email).ifPresent(user -> {

                    Map<String, String> multipleCredentials = new HashMap<>();
                    multipleCredentials.put("email", user.getEmail());
                    multipleCredentials.put("username", user.getUsername());
                    multipleCredentials.put("userId", user.getId());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    multipleCredentials,
                                    Collections.emptyList()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                });

            } catch (Exception ignored) {
                // Invalid or expired token → user remains anonymous
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
