package com.example.task_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erreur 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(
            IllegalArgumentException exception) {

        return Map.of(
                "error",
                exception.getMessage()
        );
    }

    // Erreur 409
    @ExceptionHandler(InvalidTransitionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleInvalidTransition(
            InvalidTransitionException exception) {

        return Map.of(
                "error",
                exception.getMessage()
        );
    }

    // Erreur 404
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(
            RuntimeException exception) {

        return Map.of(
                "error",
                exception.getMessage()
        );
    }
}