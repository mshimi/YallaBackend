package org.example.yalla_api.admin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to Auth Dashboard");
    }

    // Other admin endpoints
}
