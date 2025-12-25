package com.chatapp.app_backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Api api = new Api();
    private Jwt jwt = new Jwt();
    private Ws ws = new Ws();
    private Server server = new Server();

    // ========= API =========
    public static class Api {
        private String authBase;
        public String getAuthBase() { return authBase; }
        public void setAuthBase(String authBase) { this.authBase = authBase; }
    }

    // ========= JWT =========
    public static class Jwt {
        private String secretKey;
        private long expiration;
        public String getSecretKey() { return secretKey; }
        public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
        public long getExpiration() { return expiration; }
        public void setExpiration(long expiration) { this.expiration = expiration; }
    }

    // ========= WS =========
    public static class Ws {
        private String baseUrl;
        private String endpoint;
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    }

    // ========= SERVER =========
    public static class Server {
        private int port;
        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
    }

    public Api getApi() { return api; }
    public Jwt getJwt() { return jwt; }
    public Ws getWs() { return ws; }
    public Server getServer() { return server; }
}
