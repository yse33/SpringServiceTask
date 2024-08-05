package com.example.exercise.mapper;

import com.example.exercise.DTO.UserDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users);
    User fromUserRegisterDTO(UserRegisterDTO userRegisterDTO);
    UserResponseDTO toUserResponseDTO(User user);
}
