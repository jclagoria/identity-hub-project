package com.user.management.infrastructure.adapter.out;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("roles")
public class RoleEntity {

    @Id
    private long id;
    private String name;

}
