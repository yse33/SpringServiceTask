package com.example.exercise.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {
    private String firstName;
    private String surname;
}
