package com.example.exercise.controller;

import com.example.exercise.DTO.UserLoginDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody UserRegisterDTO userRegisterDTO
    ) {
        return ResponseEntity.ok(authenticationService.register(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @RequestBody UserLoginDTO userLoginDTO
    ) {
        return ResponseEntity.ok(authenticationService.login(userLoginDTO));
    }
}
