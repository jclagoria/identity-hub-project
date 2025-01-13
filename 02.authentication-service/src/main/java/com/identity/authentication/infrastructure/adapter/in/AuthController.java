package com.identity.authentication.infrastructure.adapter.in;

import com.identity.authentication.application.service.AuthService;
import com.identity.authentication.application.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> register(@RequestParam String email,
                                            @RequestParam String password) {
        return authService.registerUser(email, password)
                .flatMap(registerUser ->
                        tokenService.generateToken(registerUser.getEmail(), registerUser.getId())
                        .map(token -> ResponseEntity.status(201).body(token)));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestParam String email,
                                   @RequestParam String password) {
        return authService.loginUser(email, password)
                .flatMap(user -> tokenService.generateToken(email, user.getId()))
                .map(token -> ResponseEntity.ok().body(token));
    }

}
