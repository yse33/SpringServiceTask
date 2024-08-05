package com.example.exercise.controller;

import com.example.exercise.DTO.PermissionDTO;
import com.example.exercise.DTO.UserDTO;
import com.example.exercise.model.User;
import com.example.exercise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER_READ_ALL')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @RequestBody User user
    ) {
        return ResponseEntity.ok(userService.update(user, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String id
    ) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/grant-permissions/{id}")
    @PreAuthorize("hasAuthority('USER_GRANT_PERMISSIONS')")
    public ResponseEntity<UserDTO> grantPermissions(
            @PathVariable String id,
            @RequestBody PermissionDTO permissionDTO
            ) {
        return ResponseEntity.ok(userService.grantPermissions(id, permissionDTO));
    }

    @PostMapping("/revoke-permissions/{id}")
    @PreAuthorize("hasAuthority('USER_REVOKE_PERMISSIONS')")
    public ResponseEntity<UserDTO> revokePermissions(
            @PathVariable String id,
            @RequestBody PermissionDTO permissionDTO
    ) {
        return ResponseEntity.ok(userService.revokePermissions(id, permissionDTO));
    }
}
