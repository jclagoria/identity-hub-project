package com.identity.authentication.infrastructure.adapter.out.repository;

import com.identity.authentication.infrastructure.adapter.out.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface DataUserRepository extends R2dbcRepository<UserEntity, Long> {

    Mono<UserEntity> findByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    Mono<UserEntity> findByEmailExplicit(String email);

}
