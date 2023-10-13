package com.calendar.workout.exception;


import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
