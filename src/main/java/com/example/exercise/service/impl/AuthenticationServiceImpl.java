package com.example.exercise.service.impl;

import com.example.exercise.DTO.UserLoginDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.model.User;
import com.example.exercise.repository.UserRepository;
import com.example.exercise.security.TokenGenerator;
import com.example.exercise.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.exercise.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public UserResponseDTO register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new DuplicateKeyException("Username already exists");
        }

        User user = USER_MAPPER.fromUserRegisterDTO(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setPermissions(new ArrayList<>());
        userRepository.save(user);

        UserResponseDTO userResponseDTO = USER_MAPPER.toUserResponseDTO(user);
        userResponseDTO.setToken(tokenGenerator.generateToken(user));

        return userResponseDTO;
    }

    @Override
    public UserResponseDTO login(UserLoginDTO userLoginDTO) {
        User user;
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(),
                            userLoginDTO.getPassword()
                    )
            );
            user = (User) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            if (userRepository.existsByUsername(userLoginDTO.getUsername())) {
                throw new BadCredentialsException("Wrong password");
            } else {
                throw new BadCredentialsException("User not found");
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponseDTO userResponseDTO = USER_MAPPER.toUserResponseDTO(user);
        userResponseDTO.setToken(tokenGenerator.generateToken(user));

        return userResponseDTO;
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
