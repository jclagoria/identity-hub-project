package com.user.management.domian.port;

import com.user.management.domian.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepositoryPort {

    Mono<Role> save(Role role);
    Mono<Role> findById(long id);
    Flux<Role> findAll();
    Mono<Void> deleteById(long id);

}
