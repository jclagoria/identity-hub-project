package com.identity.authentication.domain.port;

import com.identity.authentication.domain.model.Token;
import reactor.core.publisher.Mono;

public interface TokenRepositoryPort {

    Mono<Token> save(Token token);
    Mono<Token> findByRefreshTokenToken(String refreshToken);
    Mono<Void> deleteById(long id);
    Mono<Void> invalidateTokensForUser(long userId);

}
