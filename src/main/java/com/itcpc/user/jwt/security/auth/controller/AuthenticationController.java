package com.itcpc.user.jwt.security.auth.controller;

import com.itcpc.user.jwt.security.auth.model.AuthenticationRequest;
import com.itcpc.user.jwt.security.auth.model.AuthenticationResponse;
import com.itcpc.user.jwt.security.auth.model.MyUserDetails;
import com.itcpc.user.jwt.security.auth.util.JwtUtil;
import com.itcpc.user.jwt.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        MyUserDetails userDetails = new MyUserDetails(usersRepository.findUserByUsername(authenticationRequest.getUsername()));
        String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(userDetails, jwt));
    }
}
