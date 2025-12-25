package com.chatapp.app_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

//    @Value("${jwt.secret}")
//    private String secret_key;
//
//    @Value("${jwt.expiration}")
//    private long jwt_expiration;

    private final AppProperties appProperties;

    public JwtConfig(AppProperties appProperties){
        this.appProperties = appProperties;
    }

    public String getSecret_key(){
        return appProperties.getJwt().getSecretKey();
    }

    public long getJwt_expiration(){
        return appProperties.getJwt().getExpiration();
    }

}
