package com.user.management.domian.model;

import lombok.Data;

@Data
public class UserProfile {

    private long id;
    private String email;
    private String fullName;
    private boolean emailVerified;

}
