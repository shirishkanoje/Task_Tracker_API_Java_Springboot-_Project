package com.shirish.controller;

import com.shirish.modal.User;
import com.shirish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user registration and authentication.
 * Handles basic registration logic using email and password.
 *
 * Author: Shirish Kanoje
 * Last Modified: 28 June 2025
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /** Injected repository for User persistence */
    @Autowired
    private UserRepository userRepository;

    /** Encoder to hash passwords before saving */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user if email is not already used.
     *
     * @param user User object from the request body
     * @return 201 CREATED if success, 400 BAD REQUEST if email exists
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered");
    }

    /*
    // Optional: Use this endpoint to confirm authentication if needed.
    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok("You are authenticated");
    }
    */
}
