package com.user.management.domian.port;

import com.user.management.domian.model.UserProfile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {

    Mono<UserProfile> save(UserProfile userProfile);
    Mono<UserProfile> findById(long id);
    Mono<UserProfile> findByEmail(String email);
    Flux<UserProfile> findAll();
    Mono<Void> deleteById(long id);

}
