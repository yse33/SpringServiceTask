package com.example.exercise.mapper;

import com.example.exercise.DTO.UserDTO;
import com.example.exercise.DTO.UserRegisterDTO;
import com.example.exercise.DTO.UserResponseDTO;
import com.example.exercise.model.User;
import com.example.exercise.security.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = UserMapper.class)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users);
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "stringSetToEnumSet")
    User fromUserRegisterDTO(UserRegisterDTO userRegisterDTO);
    UserResponseDTO toUserResponseDTO(User user);

    @Named("stringSetToEnumSet")
    default Set<Permission> stringSetToEnumSet(Set<String> permissions) {
        return permissions.stream()
                .map(Permission::valueOf)
                .collect(Collectors.toSet());
    }
}
