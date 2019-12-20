package com.itcpc.user.jwt.security.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    MyUserDetails userDetails;
    private String token;

}
