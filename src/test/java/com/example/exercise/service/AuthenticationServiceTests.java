package com.example.exercise.service;

import com.example.exercise.DTO.UserLoginDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.model.User;
import com.example.exercise.repository.UserRepository;
import com.example.exercise.security.TokenGenerator;
import com.example.exercise.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;
    private UserRegisterDTO registerDTO;
    private UserLoginDTO loginDTO;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("user")
                .password("password")
                .firstName("James")
                .surname("Bond")
                .permissions(new HashSet<>())
                .build();

        registerDTO = UserRegisterDTO.builder()
                .username("user")
                .password("password")
                .firstName("John")
                .surname("Doe")
                .build();

        loginDTO = UserLoginDTO.builder()
                .username("user")
                .password("password")
                .build();
    }

    @Test
    public void AuthenticationService_RegisterUser_ReturnsUserResponseDTO() {
        when(userRepository.existsByUsername(registerDTO.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenGenerator.generateToken(any(User.class))).thenReturn("token");

        UserResponseDTO responseDTO = authenticationService.register(registerDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(responseDTO.getToken()).isEqualTo("token");
    }

    @Test
    public void AuthenticationService_RegisterUser_ThrowsDuplicateKeyException() {
        when(userRepository.existsByUsername(registerDTO.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> authenticationService.register(registerDTO))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Username already exists");
    }

    @Test
    public void AuthenticationService_LoginUser_ReturnsUserResponseDTO() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenGenerator.generateToken(any(User.class))).thenReturn("token");

        UserResponseDTO responseDTO = authenticationService.login(loginDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(responseDTO.getToken()).isEqualTo("token");
    }

    @Test
    public void AuthenticationService_LoginUser_ThrowsBadCredentialsException_WrongPassword() {
        loginDTO.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication Exception"){});
        when(userRepository.existsByUsername(loginDTO.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> authenticationService.login(loginDTO))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Wrong password");
    }

    @Test
    public void AuthenticationService_LoginUser_ThrowsBadCredentialsException_UserNotFound() {
        loginDTO.setUsername("nonExistingUser");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication Exception"){});
        when(userRepository.existsByUsername(loginDTO.getUsername())).thenReturn(false);

        assertThatThrownBy(() -> authenticationService.login(loginDTO))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("User not found");
    }
}
