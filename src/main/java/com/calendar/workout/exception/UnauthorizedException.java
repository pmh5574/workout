package com.calendar.workout.exception;

public class UnauthorizedException extends CustomException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
