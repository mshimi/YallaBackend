package org.example.yalla_api.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class AllController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to Auth Dashboard");
    }

    @Qualifier("requestMappingHandlerMapping")
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("all-endpoints")
    public List<Map<String, String>> getAllEndpoints() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .map(entry -> Map.of(
                        "url", Optional.ofNullable(entry.getKey().getDirectPaths())
                                .map(directPath -> directPath.toString())
                                .orElse("N/A"),
                        "methods", Optional.ofNullable(entry.getKey().getMethodsCondition())
                                .map(methodsCondition -> methodsCondition.getMethods().toString())
                                .orElse("N/A"),
                        "handler", entry.getValue().toString()
                ))
                .collect(Collectors.toList());
    }

    // Other admin endpoints
}
