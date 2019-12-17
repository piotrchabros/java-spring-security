package com.itcpc.user.jwt.security.controller;

import com.itcpc.user.jwt.security.model.User;
import com.itcpc.user.jwt.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(
            HttpServletRequest request,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok(usersRepository.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable("id") Long id) {
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Username and password must not be empty.");
        }

        Optional<User> existingUserOptional = usersRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (!existingUser.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                if (usersRepository.findUserByUsername(newUser.getUsername()) != null) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("This username has already been taken.");
                }
            } else {
                existingUser.setUsername(newUser.getUsername());
            }

            existingUser.setName(newUser.getName());
            existingUser.setSurname(newUser.getSurname());

            String hashed = passwordEncoder.encode(newUser.getPassword());
            existingUser.setPassword(hashed);

            existingUser.setCompany(newUser.getCompany());
            existingUser.setCountry(newUser.getCountry());
            existingUser.setEmployees(newUser.getEmployees());
            existingUser.setName(newUser.getName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setEnabled(newUser.isEnabled());
            existingUser.setLanguage(newUser.getLanguage());
            existingUser.setPhone(newUser.getPhone());
            existingUser.setRoles(newUser.getRoles());

            return ResponseEntity.ok(usersRepository.save(existingUser));
        } else {
            newUser.setId(id);
            return ResponseEntity.ok(usersRepository.save(newUser));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
        if (usersRepository.findUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("This username has already been taken.");
        }
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Username and password must not be empty.");
        }
        String hashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashed);

        User savedUser = usersRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        usersRepository.deleteById(id);
    }

}
