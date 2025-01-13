package com.identity.authentication.infrastructure.adapter.out.repository;

import com.identity.authentication.domain.model.Token;
import com.identity.authentication.domain.port.TokenRepositoryPort;
import com.identity.authentication.infrastructure.adapter.out.entity.TokenEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class R2dbcTokenRepositoryAdapter implements TokenRepositoryPort {

    private final DataTokenRepository dataTokenRepository;

    @Override
    public Mono<Token> save(Token token) {

        TokenEntity entity = new TokenEntity();
        entity.setAccessToken(token.getAccessToken());
        entity.setRefreshToken(token.getRefreshToken());
        entity.setUserId(token.getUserId());
        entity.setExpiresAt(token.getExpiresAt());

        return dataTokenRepository.save(entity)
                .map(saved -> {
                    token.setId(saved.getId());
                    return token;
                });
    }

    @Override
    public Mono<Token> findByRefreshTokenToken(String refreshToken) {
        return dataTokenRepository
                .findByRefreshToken(refreshToken)
                .map(tokenEntity -> {
                    Token token = new Token();
                    token.setId(tokenEntity.getId());
                    token.setAccessToken(tokenEntity.getAccessToken());
                    token.setRefreshToken(tokenEntity.getRefreshToken());
                    token.setUserId(tokenEntity.getUserId());
                    token.setExpiresAt(tokenEntity.getExpiresAt());

                    return token;
                });
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return dataTokenRepository.deleteById(id);
    }

    @Override
    public Mono<Void> invalidateTokensForUser(long userId) {
        return null;
    }
}

