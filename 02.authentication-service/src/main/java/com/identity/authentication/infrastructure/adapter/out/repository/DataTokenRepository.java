package com.identity.authentication.infrastructure.adapter.out.repository;

import com.identity.authentication.infrastructure.adapter.out.entity.TokenEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface DataTokenRepository extends R2dbcRepository<TokenEntity, Long> {

    Mono<TokenEntity> findByRefreshToken(String refreshToken);

}
