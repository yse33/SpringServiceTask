package com.example.exercise.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {
    private String username;
    private String password;
}
