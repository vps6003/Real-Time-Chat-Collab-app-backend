package com.chatapp.app_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key jwtKey;
    private final long jwtExpiration;

    public JwtUtil(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION}") long jwtExpiration
    ){
        this.jwtExpiration = jwtExpiration;
        this.jwtKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String id){
        return Jwts.builder()
                .setSubject(id)
                .claim("email",email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(jwtKey)
                .compact();

    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

}
