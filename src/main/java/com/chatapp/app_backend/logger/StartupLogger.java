package com.chatapp.app_backend.logger;

import com.chatapp.app_backend.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

//    @Value("${app.ws.base-url}")
//    private String wsBaseUrl;
//
//    @Value("${app.ws.endpoint}")
//    private String wsEndpoint;
//
//    @Value("${app.api.auth-base}")
//    private String authBasePath;
//
//    @Value("${server.port:}")
//    private String serverPort;
//
//    @Value("${app.env}")
//    private  String environment;

    AppProperties appProperties;

    public StartupLogger(AppProperties appProperties){
        this.appProperties = appProperties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printStartupInfo() {
        logger.info("==============================================");
        logger.info("🚀 Application started successfully!");
//        logger.info("🚀 Application environment : {}" ,appProperties.environment);
        logger.info("👉 REST Server: http://localhost:" + appProperties.getServer().getPort());
        logger.info("👉 Auth API Base: http://localhost:" + appProperties.getServer().getPort() + appProperties.getApi().getAuthBase());
        logger.info("👉 WebSocket Base URL: {}", appProperties.getWs().getBaseUrl());
        logger.info("👉 WebSocket Endpoint: {}", appProperties.getWs().getEndpoint());
        logger.info("👉 Full WebSocket URL: {}{}", appProperties.getWs().getBaseUrl(), appProperties.getWs().getEndpoint());
        logger.info("==============================================");
    }
}
