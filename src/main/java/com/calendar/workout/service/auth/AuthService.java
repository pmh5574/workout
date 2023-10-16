package com.calendar.workout.service.auth;

import com.calendar.workout.dto.auth.request.LoginRequest;
import com.calendar.workout.service.auth.mapper.OauthMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final OauthMappers oauthMappers;

    public boolean isCertified(String token) {
        return false;
    }

    public LoginTokens login(String oauthType, LoginRequest loginRequest) {
        convertToOauthToken
    }
}
