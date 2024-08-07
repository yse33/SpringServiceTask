package com.example.exercise.service;

import com.example.exercise.DTO.PermissionDTO;
import com.example.exercise.DTO.UserDTO;
import com.example.exercise.model.User;

import java.util.List;

public interface UserService {
    UserDTO getById(String id);
    List<UserDTO> getAll();
    UserDTO update(User user, String id);
    void delete(String id);
    UserDTO grantPermissions(String id, PermissionDTO permissionDTO);
    UserDTO revokePermissions(String id, PermissionDTO permissionDTO);
}
