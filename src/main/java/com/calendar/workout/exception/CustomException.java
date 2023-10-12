package com.calendar.workout.exception;


import java.util.HashMap;
import java.util.Map;

public abstract class CustomException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    protected abstract int getStatusCode();
}
