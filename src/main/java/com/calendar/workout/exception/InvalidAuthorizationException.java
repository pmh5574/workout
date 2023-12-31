package com.calendar.workout.exception;

public class InvalidAuthorizationException extends CustomException {

    private static final String MESSAGE = "유효 하지 않은 인증 코드 입니다.";

    public InvalidAuthorizationException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
