package com.calendar.workout.exception;

public class InvalidAccessTokenException extends CustomException {

    private static final String MESSAGE = "유효 하지 않은 엑세스 토큰 입니다.";

    public InvalidAccessTokenException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
