package com.identity.authentication.infrastructure.adapter.out.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("users")
@Data
public class UserEntity {

    @Id
    private long id;

    private String email;

    @Column("password_hash")
    private String passwordHash;

    @Column("is_email_verified")
    private boolean emailVerified;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

}
