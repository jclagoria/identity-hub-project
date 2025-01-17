package com.user.management.application.service;

import com.user.management.domian.model.Role;
import com.user.management.domian.port.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepositoryPort roleRepositoryPort;

    public Mono<Role> createRole(String name) {
        Role role = new Role();
        role.setName(name);

        return roleRepositoryPort.save(role);
    }

    public Mono<Role> findRoleById(long id) {
        return roleRepositoryPort.findById(id);
    }

    public Flux<Role> findAllRoles() {
        return roleRepositoryPort.findAll();
    }

    public Mono<Void> deleteRoleById(long id) {
        return roleRepositoryPort.deleteById(id);
    }

}
