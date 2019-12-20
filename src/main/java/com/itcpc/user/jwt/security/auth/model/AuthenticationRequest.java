package com.itcpc.user.jwt.security.auth.model;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
