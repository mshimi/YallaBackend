package org.example.yalla_api.config;

import org.example.yalla_api.common.exceptions.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Hibernate-specific constraint violation exceptions
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String message = "Validation failed due to a constraint violation.";
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(message)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle Spring Data-specific data integrity violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String message = handleDataIntegrityViolation(ex);
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(message)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        List<String> details = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Validation Failure",
                HttpStatus.BAD_REQUEST.value(),
                details
        );

        return ResponseEntity.badRequest().body(error);
    }

    // Handle @Valid annotation errors (for DTOs or form requests)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        System.out.println("Exception Caught: " + ex.getClass());
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Validation Failed",
                HttpStatus.BAD_REQUEST.value(),
                details
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle general SQL exceptions
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex, WebRequest request) {
        String message = handleSQLException(ex);
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(message)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Helper to process DataIntegrityViolationException
    private String handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = ex.getMessage().toLowerCase();

        if (errorMessage.contains("unique constraint")) {
            return parseUniqueConstraintViolationMessage(errorMessage);
        } else if (errorMessage.contains("foreign key constraint")) {
            return "Foreign key constraint violated. Related entity does not exist.";
        } else if (errorMessage.contains("integrity constraint")) {
            return "Data integrity constraint violated.";
        }
        return "Data integrity violation.";
    }

    // Helper to process SQLException
    private String handleSQLException(SQLException ex) {
        String sqlState = ex.getSQLState();

        if ("23505".equals(sqlState)) { // Unique constraint violation
            return "Duplicate entry detected.";
        } else if ("23503".equals(sqlState)) { // Foreign key violation
            return "Invalid reference. Related entity does not exist.";
        } else if ("22001".equals(sqlState)) { // Data too long for column
            return "Data value too large for column.";
        }
        return "An unexpected database error occurred.";
    }

    // Extract field name for unique constraint violations
    private String parseUniqueConstraintViolationMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("key \\((.*?)\\)=");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            String fieldName = matcher.group(1).replace("_", " ");
            return capitalizeFirstLetter(fieldName) + " already exists.";
        }
        return "Duplicate entry exists.";
    }

    // Capitalize helper
    private String capitalizeFirstLetter(String field) {
        if (field == null || field.isEmpty()) return field;
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Access Denied",
                HttpStatus.FORBIDDEN.value(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Fallback for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}