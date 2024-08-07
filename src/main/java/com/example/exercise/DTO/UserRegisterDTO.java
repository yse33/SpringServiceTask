package com.example.exercise.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String surname;
}
