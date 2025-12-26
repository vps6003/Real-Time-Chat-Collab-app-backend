package com.chatapp.app_backend.config;

import com.chatapp.app_backend.security.JwtFilter;
import com.chatapp.app_backend.security.handler.CustomAccessDeniedHandler;
import com.chatapp.app_backend.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    // Inject auth base path from application-local.properties
//    @Value("${app.api.auth-base}")
//    private String authBase;

    private final AppProperties appProperties;

    // Custom JWT filter
    private final JwtFilter jwtFilter;

    // Custom handlers to return JSON instead of HTML
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            JwtFilter jwtFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler,
            AppProperties appProperties,
            CorsConfigurationSource corsConfigurationSource
    ) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtFilter = jwtFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.appProperties = appProperties;
    }

    /**
     * Main Spring Security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Explicit CORS configuration (NEW WAY)
                .cors(cors -> cors.configurationSource(this.corsConfigurationSource));

        // Disable CSRF (JWT is stateless, no sessions)
        http.csrf(csrf -> csrf.disable());

        // Stateless session management (JWT based auth)
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Authorization rules
        http.authorizeHttpRequests(auth -> auth
                // Allow login/register without token
                .requestMatchers(appProperties.getApi().getAuthBase() + "/**").permitAll()
//                        .requestMatchers("/vps_chat_room/auth/**").permitAll()
                // All other APIs require authentication
                .anyRequest().authenticated()
        );

        // Use custom handlers to avoid HTML responses
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint) // 401 JSON
                .accessDeniedHandler(accessDeniedHandler)             // 403 JSON
        );

        // Register JWT filter before default username/password auth filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * AuthenticationManager bean
     * Required internally by Spring Security
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Password encoder for hashing passwords
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
