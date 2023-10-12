package com.calendar.workout.exception;

// 404
public class PostsNotFound extends CustomException {

    private static final String MESSAGE = "존재 하지 않는 글 입니다.";
    public PostsNotFound() {
        super(MESSAGE);
    }

    @Override
    protected int getStatusCode() {
        return 404;
    }

}
