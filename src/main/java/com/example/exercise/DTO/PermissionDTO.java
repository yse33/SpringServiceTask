package com.example.exercise.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class PermissionDTO {
    private Set<String> permissions;
}
