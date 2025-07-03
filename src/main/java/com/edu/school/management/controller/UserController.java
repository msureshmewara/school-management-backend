package com.edu.school.management.controller;

import com.edu.school.management.entity.UserEntity;
import com.edu.school.management.exceptions.InvalidCredentialsException;
import com.edu.school.management.response.ApiResponse;
import com.edu.school.management.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // ✅ Required for annotations

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserEntity>> loginUser(@RequestBody UserEntity user) {
        return userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())
            .map(foundUser -> ResponseEntity.ok(
                ApiResponse.<UserEntity>builder()
                    .status(200)
                    .message("Login successful")
                    .data(foundUser)
                    .build()
            ))
            .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
