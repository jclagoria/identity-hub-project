package com.user.management.infrastructure.adapter.out;

import com.user.management.domian.model.Role;
import com.user.management.domian.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class R2dbcRoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleR3dbcRepository roleR3dbcRepository;

    @Override
    public Mono<Role> save(Role role) {

        RoleEntity entity = new RoleEntity();
        entity.setName(role.getName());

        return roleR3dbcRepository.save(entity)
                .map(saved -> {
                    role.setId(saved.getId());

                    return role;
                });
    }

    @Override
    public Mono<Role> findById(long id) {
        return roleR3dbcRepository.findById(id)
                .map(entity -> {
                    Role role = new Role();
                    role.setId(entity.getId());
                    role.setName(entity.getName());

                    return role;
                });
    }

    @Override
    public Flux<Role> findAll() {
        return roleR3dbcRepository.findAll()
                .map(entity -> {
                    Role role = new Role();
                    role.setId(entity.getId());
                    role.setName(entity.getName());

                    return role;
                });
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return roleR3dbcRepository.deleteById(id);
    }
}
