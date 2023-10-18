package com.calendar.workout.dto.auth.response;

public record GoogleUserInfo(
        String id,
        String email,
        String name
) {
}
