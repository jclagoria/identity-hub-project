package com.identity.authentication.application.service;

import com.identity.authentication.domain.model.Token;
import com.identity.authentication.domain.port.TokenRepositoryPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    private final TokenRepositoryPort tokenRepositoryPort;

    public TokenService(TokenRepositoryPort tokenRepositoryPort) {
        this.tokenRepositoryPort = tokenRepositoryPort;
    }

    public Mono<Token> generateToken(String email, long userId) {
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        String refreshToken = /* Generar un token de refresco único (UUID, etc.) */
                "refresh-" + userId + "-" + System.currentTimeMillis();

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setUserId(userId);
        token.setExpiresAt(Instant.ofEpochSecond(System.currentTimeMillis() + expirationMillis));

        return tokenRepositoryPort.save(token);
    }

}
