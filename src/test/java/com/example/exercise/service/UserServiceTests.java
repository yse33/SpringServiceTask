package com.example.exercise.service;

import com.example.exercise.DTO.PermissionDTO;
import com.example.exercise.DTO.UserDTO;
import com.example.exercise.model.User;
import com.example.exercise.repository.UserRepository;
import com.example.exercise.security.Permission;
import com.example.exercise.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User secondUser;
    private PermissionDTO permissionDTO;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("user")
                .password("password")
                .firstName("User")
                .surname("Name")
                .permissions(new ArrayList<>(List.of(Permission.USER_READ_ALL, Permission.USER_READ)))
                .build();

        secondUser = User.builder()
                .username("secondUser")
                .password("password")
                .firstName("Second")
                .surname("User")
                .permissions(new ArrayList<>(List.of(Permission.USER_READ)))
                .build();

        permissionDTO = PermissionDTO.builder()
                .permissions(new ArrayList<>(List.of("USER_UPDATE")))
                .build();
    }

    @Test
    public void UserService_GetById_ReturnsUserDTO() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));

        UserDTO userDTO = userService.getById("id");

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDTO.getSurname()).isEqualTo(user.getSurname());
        assertThat(userDTO.getPermissions()).isEqualTo(user.getPermissions());
    }

    @Test
    public void UserService_GetById_ReturnsNull() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        UserDTO userDTO = userService.getById("id");

        assertThat(userDTO).isNull();
    }

    @Test
    public void UserService_GetAll_ReturnsUserDTOs() {
        when(userRepository.findAll()).thenReturn(java.util.List.of(user, secondUser));

        List<UserDTO> userDTOs = userService.getAll();

        assertThat(userDTOs).isNotNull();
        assertThat(userDTOs).hasSize(2);
        assertThat(userDTOs.get(0).getUsername()).isEqualTo(user.getUsername());
        assertThat(userDTOs.get(1).getUsername()).isEqualTo(secondUser.getUsername());
    }

    @Test
    public void UserService_Update_ReturnsUserDTO() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.update(secondUser, "id");

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo(secondUser.getUsername());
        assertThat(userDTO.getFirstName()).isEqualTo(secondUser.getFirstName());
        assertThat(userDTO.getSurname()).isEqualTo(secondUser.getSurname());
    }

    @Test
    public void UserService_Update_ThrowsRuntimeException() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.update(secondUser, "id"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    public void UserService_GrantPermissions_ReturnsUserDTO() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.grantPermissions("id", permissionDTO);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getPermissions()).contains(Permission.USER_READ_ALL, Permission.USER_READ, Permission.USER_UPDATE);
    }

    @Test
    public void UserService_GrantPermissions_ThrowsRuntimeException_GrantedPermission() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));

        user.getPermissions().add(Permission.USER_UPDATE);

        assertThatThrownBy(() -> userService.grantPermissions("id", permissionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Permission 'USER_UPDATE' already granted");
    }

    @Test
    public void UserService_GrantPermissions_ThrowsRuntimeException_UserNotFound() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.grantPermissions("id", permissionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    public void UserService_RevokePermissions_ReturnsUserDTO() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.getPermissions().add(Permission.USER_UPDATE);

        UserDTO userDTO = userService.revokePermissions("id", permissionDTO);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getPermissions()).contains(Permission.USER_READ, Permission.USER_READ_ALL);
        assertThat(userDTO.getPermissions()).doesNotContain(Permission.USER_UPDATE);
    }

    @Test
    public void UserService_RevokePermissions_ThrowsRuntimeException_NotGrantedPermission() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));

        assertThatThrownBy(() -> userService.revokePermissions("id", permissionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Permission 'USER_UPDATE' not granted");
    }

    @Test
    public void UserService_RevokePermissions_ThrowsRuntimeException_UserNotFound() {
        when(userRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.revokePermissions("id", permissionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }
}
