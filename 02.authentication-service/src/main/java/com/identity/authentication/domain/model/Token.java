package com.identity.authentication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private long id;
    private long userId;
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;

}
