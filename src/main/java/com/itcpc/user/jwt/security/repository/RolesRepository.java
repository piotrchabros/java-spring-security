package com.itcpc.user.jwt.security.repository;

import com.itcpc.user.jwt.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
