package com.identity.authentication.infrastructure.adapter.out.repository;

import com.identity.authentication.domain.model.UserAuthentication;
import com.identity.authentication.domain.port.UserRepositoryPort;
import com.identity.authentication.exception.CustomException;
import com.identity.authentication.infrastructure.adapter.out.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class R2dbcUserRepositoryAdapter implements UserRepositoryPort {

    private final DataUserRepository dataUserRepository;

    @Override
    public Mono<UserAuthentication> save(UserAuthentication userAuthentication) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userAuthentication.getEmail());
        userEntity.setPasswordHash(userAuthentication.getPasswordHash());
        userEntity.setEmailVerified(userAuthentication.isEmailVerified());

        return dataUserRepository
                .save(userEntity).map(saved -> {
                    userAuthentication.setId(saved.getId());
                    return userAuthentication;
                });
    }

    @Override
    public Mono<UserAuthentication> findByEmail(String email) {
        return dataUserRepository
                .findByEmail(email)
                .map(entity -> {
                    UserAuthentication userAuthentication = new UserAuthentication();
                    userAuthentication.setId(entity.getId());
                    userAuthentication.setEmail(entity.getEmail());
                    userAuthentication.setPasswordHash(entity.getPasswordHash());
                    userAuthentication.setEmailVerified(entity.isEmailVerified());

                    return userAuthentication;
                });
    }

    @Override
    public Mono<UserAuthentication> findById(long id) {
        return dataUserRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException.UserNotFoundException(String.valueOf(id))))
                .map(entity -> {
                    UserAuthentication userAuthentication = new UserAuthentication();
                    userAuthentication.setId(entity.getId());
                    userAuthentication.setEmail(entity.getEmail());
                    userAuthentication.setPasswordHash(entity.getPasswordHash());
                    userAuthentication.setEmailVerified(entity.isEmailVerified());

                    return userAuthentication;
                });
    }
}
