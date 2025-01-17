package com.user.management.infrastructure.adapter.out;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class UserEntity {

    @Id
    private long id;

    private String email;

    @Column("full_name")
    private String fullName;

    private boolean emailVerified;

}
