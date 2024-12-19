package org.example.yalla_api.common.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private List<String> details;

    public ErrorResponse(LocalDateTime timestamp, String message, int status, List<String> details) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.details = details;
    }

    // Getters and Setters
}
