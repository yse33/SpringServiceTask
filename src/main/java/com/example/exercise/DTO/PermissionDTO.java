package com.example.exercise.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PermissionDTO {
    private List<String> permissions;
}
