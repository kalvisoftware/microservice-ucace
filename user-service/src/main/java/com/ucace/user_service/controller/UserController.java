package com.ucace.user_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ucace.user_service.dto.UserRequestDTO;
import com.ucace.user_service.dto.UserResponseDTO;
import com.ucace.user_service.response.ApiResponseDTO;
import com.ucace.user_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> saveUser(
            @RequestBody UserRequestDTO request) {

        UserResponseDTO savedUser = userService.saveUser(request);

        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "User created successfully",
                savedUser,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers() {

        List<UserResponseDTO> users = userService.getAllUsers();

        ApiResponseDTO<List<UserResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "Users fetched successfully",
                users,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(
            @PathVariable Long id) {

        UserResponseDTO user = userService.getUserById(id);

        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "User fetched successfully",
                user,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request) {

        UserResponseDTO updatedUser = userService.updateUser(id, request);

        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "User updated successfully",
                updatedUser,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteUser(
            @PathVariable Long id) {

        String message = userService.deleteUser(id);

        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "User deleted successfully",
                message,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}