package com.example.exercise.service;

import com.example.exercise.DTO.UserLoginDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.model.User;

public interface AuthenticationService {
    UserResponseDTO register(UserRegisterDTO userRegisterDTO);
    UserResponseDTO login(UserLoginDTO userLoginDTO);
    User getCurrentUser();
}
