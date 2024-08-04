package com.example.exercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloWorldController {
    @GetMapping("/helloworld")
    @PreAuthorize("hasAuthority('HELLO_WORLD_READ')")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("helloworld");
    }
}
