package com.example.scheduleproject.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse(int value, String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.status = value;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;

    }
}
