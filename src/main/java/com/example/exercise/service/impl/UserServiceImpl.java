package com.example.exercise.service.impl;

import com.example.exercise.DTO.UserDTO;
import com.example.exercise.model.User;
import com.example.exercise.repository.UserRepository;
import com.example.exercise.security.Permission;
import com.example.exercise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.exercise.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO getById(String id) {
        return USER_MAPPER.toUserDTO(userRepository.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAll() {
        return USER_MAPPER.toUserDTOs(userRepository.findAll());
    }

    @Override
    public UserDTO update(User user, String id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSurname(user.getSurname());

        return USER_MAPPER.toUserDTO(userRepository.save(existingUser));
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO grantPermissions(String id, Set<String> permissions) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        for (String permission : permissions) {
            if (!existingUser.getPermissions().contains(Permission.valueOf(permission))) {
                existingUser.getPermissions().add(Permission.valueOf(permission));
            } else {
                throw new RuntimeException("Permission '" + permission + "' already granted");
            }
        }

        return USER_MAPPER.toUserDTO(userRepository.save(existingUser));
    }
}
