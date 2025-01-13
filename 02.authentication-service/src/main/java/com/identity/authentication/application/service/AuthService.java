package com.identity.authentication.application.service;

import com.identity.authentication.domain.model.UserAuthentication;
import com.identity.authentication.domain.port.UserRepositoryPort;
import com.identity.authentication.exception.CustomException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final UserRepositoryPort userRepositoryPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepositoryPort userRepository) {
        this.userRepositoryPort = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public Mono<UserAuthentication> registerUser(String email, String rawPassword) {
        return userRepositoryPort.findByEmail(email)
                .flatMap(existing -> Mono.error(new CustomException.UserAlreadyExistsException("User already exists")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            UserAuthentication newUser = new UserAuthentication();
                            newUser.setEmail(email);
                            newUser.setPasswordHash(bCryptPasswordEncoder.encode(rawPassword));
                            newUser.setEmailVerified(false);

                            return userRepositoryPort.save(newUser);
                        })
                ).cast(UserAuthentication.class);
    }

    public Mono<UserAuthentication> loginUser(String email, String rawPassword) {
        return userRepositoryPort.findByEmail(email)
                .switchIfEmpty(Mono.error(new CustomException
                        .UserNotFoundException("User not found")))
                .flatMap( user -> {
                    if (!bCryptPasswordEncoder.matches(rawPassword, user.getPasswordHash())) {
                        return Mono.error(new CustomException
                                .AuthenticationException("Invalid credentials"));
                    }

                    return Mono.just(user);
                });
    }

}
