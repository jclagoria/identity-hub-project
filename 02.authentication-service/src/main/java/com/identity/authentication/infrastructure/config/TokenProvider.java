package com.identity.authentication.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expirationMilliseconds;

    /**
     * Genera un token de acceso (JWT) con el userId como 'subject'.
     */
    public String generateToken(long userId, String email) {

       long now = System.currentTimeMillis();
       return Jwts.builder()
               .setSubject(String.valueOf(userId))
               .setIssuedAt(new Date(now))
               .setExpiration(new Date(now + expirationMilliseconds))
               .signWith(SignatureAlgorithm.HS256, jwtSecret)
               .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return null;
    }

}
