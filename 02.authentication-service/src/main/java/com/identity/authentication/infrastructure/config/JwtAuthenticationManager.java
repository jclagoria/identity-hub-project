package com.identity.authentication.infrastructure.config;

import com.identity.authentication.domain.model.UserAuthentication;
import com.identity.authentication.domain.port.UserRepositoryPort;
import com.identity.authentication.infrastructure.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenProvider tokenProvider;
    private final UserRepositoryPort userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {

        String authToken = authentication.getCredentials().toString();

        Jws<Claims> jwsClaims;

        try {
            jwsClaims = tokenProvider.parseToken(authToken);
        } catch (Exception e) {
            return Mono.error(new BadCredentialsException("Invalid token"));
        }

        String userIdString = jwsClaims.getBody().getSubject();
        long userId;

        try {
            userId = Long.parseLong(userIdString);
        } catch (NumberFormatException ex) {
            return Mono.error(new BadCredentialsException("Invalid user ID in token"));
        }

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(user -> buildAuthenticationToken(user, authToken));
    }

    private Authentication buildAuthenticationToken(UserAuthentication user, String authToken) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return new UsernamePasswordAuthenticationToken(
                userPrincipal,
                authToken,
                Collections.singletonList(authority));
    }

}
