package com.example.exercise.service;

import com.example.exercise.DTO.UserDTO;
import com.example.exercise.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDTO getById(String id);
    List<UserDTO> getAll();
    UserDTO update(User user, String id);
    void delete(String id);
    UserDTO grantPermissions(String id, Set<String> permissions);
}
