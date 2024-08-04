package com.example.exercise.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String surname;
    private Set<String> permissions;
}
