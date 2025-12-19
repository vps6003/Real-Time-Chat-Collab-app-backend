package com.chatapp.app_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${JWT_SECRET}")
    private String secret_key;

    @Value("${JWT_EXPIRATION}")
    private long jwt_expiration;

    public String getSecret_key(){
        return secret_key;
    }

    public long getJwt_expiration(){
        return jwt_expiration;
    }

}
