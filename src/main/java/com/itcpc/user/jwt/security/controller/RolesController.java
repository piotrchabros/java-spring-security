package com.itcpc.user.jwt.security.controller;

import com.itcpc.user.jwt.security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RolesController {

    @Autowired
    RolesRepository rolesRepository;

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(rolesRepository.findAll());
    }
}
