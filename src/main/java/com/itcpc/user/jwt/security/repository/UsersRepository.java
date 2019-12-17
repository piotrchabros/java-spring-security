package com.itcpc.user.jwt.security.repository;

import com.itcpc.user.jwt.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

}
