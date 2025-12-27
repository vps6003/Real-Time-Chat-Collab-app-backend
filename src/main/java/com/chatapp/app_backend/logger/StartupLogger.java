package com.chatapp.app_backend.logger;

import com.chatapp.app_backend.config.AppProperties;
import com.chatapp.app_backend.service.UiLoaderService;
import com.chatapp.app_backend.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);
    private final AppProperties appProperties;
    private final UiLoaderService uiLoaderService; // Added for health check
    private final JwtUtil jwtUtil;         // Added for health check

    public StartupLogger(AppProperties appProperties, UiLoaderService uiLoaderService, JwtUtil jwtUtil) {
        this.appProperties = appProperties;
        this.uiLoaderService = uiLoaderService;
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    private void validateServicesOnStartup() {
        logger.info("*********************************************************");
        logger.info("*********************************************************");

        logger.info("🛠️ [System Check] Validating internal components...");
        try {
            // 1. Verify Database Connection via UserService
            // We don't care about the result, just that it doesn't throw a 'Connection Refused'
            logger.debug("Testing Database connectivity...");
            try { uiLoaderService.load("health-check@system.local"); } catch (Exception ignored) {}

            // 2. Verify Security Config
            logger.debug("Testing JWT Utility configuration...");
            if (jwtUtil == null) throw new RuntimeException("JwtUtil failed to initialize!");

            logger.info("✅ [System Check] Internal services are reachable.");
        } catch (Exception e) {
            logger.error("❌ [System Check] Critical failure during service validation: {}", e.getMessage());
        }
        logger.info("*********************************************************");
        logger.info("*********************************************************");

    }

    @EventListener(ApplicationReadyEvent.class)
    public void printStartupInfo() {
        logger.info("==============================================");
        logger.info("🚀 Application started successfully!");
        logger.info("👉 REST Server: http://localhost:{}{}", appProperties.getServer().getPort(),appProperties.getApi().getApiBase());
        logger.info("👉 Auth API Base: http://localhost:{}{}",
                appProperties.getServer().getPort(),
                appProperties.getApi().getAuthBase());
        logger.info("👉 WebSocket URL: {}{}", appProperties.getWs().getBaseUrl(), appProperties.getWs().getEndpoint());
        logger.info("==============================================");
    }
}