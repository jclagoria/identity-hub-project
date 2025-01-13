package com.identity.authentication.infrastructure.adapter.out.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name = "tokens")
@Data
public class TokenEntity {

    @Id
    private long id;

    @Column("user_id")
    private long userId;

    @Column("access_token")
    private String accessToken;

    @Column("refresh_token")
    private String refreshToken;

    @Column("expires_at")
    private Instant expiresAt;

}
