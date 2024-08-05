package com.example.exercise.DTO;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String surname;
}
