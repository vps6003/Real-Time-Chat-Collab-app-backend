package com.chatapp.app_backend.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    @Value("${app.ws.base-url}")
    private String wsBaseUrl;

    @Value("${app.ws.endpoint}")
    private String wsEndpoint;

    @Value("${app.api.auth-base}")
    private String authBasePath;

    @Value("${server.port:}")
    private String serverPort;

    @Value("${app.env}")
    private  String environment;

    @EventListener(ApplicationReadyEvent.class)
    public void printStartupInfo() {
        logger.info("==============================================");
        logger.info("🚀 Application started successfully!");
        logger.info("🚀 Application environment : {}" ,environment);
        logger.info("👉 REST Server: http://localhost:" + serverPort);
        logger.info("👉 Auth API Base: http://localhost:" + serverPort + authBasePath);
        logger.info("👉 WebSocket Base URL: {}", wsBaseUrl);
        logger.info("👉 WebSocket Endpoint: {}", wsEndpoint);
        logger.info("👉 Full WebSocket URL: {}{}", wsBaseUrl, wsEndpoint);
        logger.info("==============================================");
    }
}
