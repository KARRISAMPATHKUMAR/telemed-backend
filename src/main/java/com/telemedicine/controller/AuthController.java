package com.telemedicine.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.telemedicine.model.User;
import com.telemedicine.repository.UserRepository;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository repository;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    // Register User
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        Optional<User> existingUser = repository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return "Email already exists!";
        }

        repository.save(user);

        return "Registration Successful!";
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        Optional<User> user = repository.findByEmail(loginUser.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (!user.get().getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        return ResponseEntity.ok(user.get());
    }
}