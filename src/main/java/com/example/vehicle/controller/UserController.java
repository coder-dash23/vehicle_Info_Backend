package com.example.vehicle.controller;
import com.example.vehicle.config.JwtTokenUtil;
import com.example.vehicle.model.LoginResponse;
import com.example.vehicle.model.User;
import com.example.vehicle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email already exists"));
        }
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error creating user: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            User foundUser = existingUser.get();
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                String jwtToken = JwtTokenUtil.generateToken(foundUser.getEmail());
                System.out.println("Token: " + jwtToken);
                return ResponseEntity.ok(new LoginResponse("Login Successful", jwtToken, foundUser.getEmail(), foundUser.getName()));
            } else {
                System.out.println("Password Mismatch!"); // Debugging message
            }
        } else {
            System.out.println("User Not Found!"); // Debugging message
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid Credentials"));
    }
}
