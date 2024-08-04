package com.example.exercise.DTO;

import com.example.exercise.security.Permission;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Set;

@Data
public class UserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String username;
    private String firstName;
    private String surname;
    private Set<Permission> permissions;
}
