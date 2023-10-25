package com.calendar.workout.dto.auth;

import lombok.Builder;

public record AuthTokens(String accessToken, String refreshToken) {

    @Builder
    public AuthTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
