package com.calendar.workout.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String code;

    public LoginRequest(String code) {
        this.code = code;
    }
}
