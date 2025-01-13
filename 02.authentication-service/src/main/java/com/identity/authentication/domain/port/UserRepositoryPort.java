package com.identity.authentication.domain.port;

import com.identity.authentication.domain.model.UserAuthentication;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {

    Mono<UserAuthentication> save(UserAuthentication userAuthentication);
    Mono<UserAuthentication> findByEmail(String email);
    Mono<UserAuthentication> findById(long id);
}
