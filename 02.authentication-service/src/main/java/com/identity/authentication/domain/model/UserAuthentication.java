package com.identity.authentication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthentication {

    private long id;
    private String email;
    private String passwordHash;
    private boolean isEmailVerified;
    private Instant createdAt;
    private Instant updatedAt;

}
