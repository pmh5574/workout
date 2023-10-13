package com.calendar.workout.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    public boolean isCertified(String token) {
        return false;
    }
}
