package com.itcpc.user.jwt.security.api.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "ROLE")
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    private String name;
}
