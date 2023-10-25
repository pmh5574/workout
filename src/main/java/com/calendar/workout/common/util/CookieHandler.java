package com.calendar.workout.common.util;

import org.springframework.http.ResponseCookie;

public class CookieHandler {

    public static final int COOKIE_AGE_SECONDS = 259200;

    public static ResponseCookie getResponseCookie(String refreshToken) {
        return ResponseCookie.from("refresh-token", refreshToken)
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
