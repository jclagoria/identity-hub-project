package com.user.management.application.service;

import com.user.management.domian.model.UserProfile;
import com.user.management.domian.port.UserRepositoryPort;
import com.user.management.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    public Mono<UserProfile> createUser(String email, String fullName) {
        return userRepositoryPort.findByEmail(email)
                .flatMap(existing -> Mono
                        .error(new CustomException
                                .UserAlreadyExists("User with email " + email + " exists!")))
                .switchIfEmpty(Mono.defer(() -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setEmail(email);
                    userProfile.setFullName(fullName);
                    userProfile.setEmailVerified(false);

                    return userRepositoryPort.save(userProfile);
                })).cast(UserProfile.class);
    }

    public Mono<UserProfile> updateUser(long userId, String fullName) {
        return userRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new CustomException.UserNotFound("User not found")))
                .flatMap(user -> {
                    user.setFullName(fullName);
                    return userRepositoryPort.save(user);
                });
    }

    public Mono<UserProfile> getUserById(long userId) {
        return userRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new CustomException.UserNotFound("User not found")));
    }

    public Flux<UserProfile> listAllUsers() {
        return userRepositoryPort.findAll();
    }

    public Mono<Void> deleteUser(long userId) {
        return userRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new CustomException.UserNotFound("User not found")))
                .flatMap(user -> userRepositoryPort.deleteById(user.getId()));
    }

}
