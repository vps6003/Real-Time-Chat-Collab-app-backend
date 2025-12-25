package com.chatapp.app_backend.security;

import com.chatapp.app_backend.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
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
          AppProperties appProperties
    ){


        byte[] keyBytes = Decoders.BASE64.decode(
                appProperties.getJwt().getSecretKey()
        );

        this.jwtKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpiration = appProperties.getJwt().getExpiration();
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
        Claims check =  Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
//        System.out.println(check);
        return check;
    }

    public boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

}
