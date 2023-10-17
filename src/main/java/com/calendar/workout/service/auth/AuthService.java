package com.calendar.workout.service.auth;

import com.calendar.workout.service.auth.mapper.OauthMapper;
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

    public void login(String oauthType, String code) {
        OauthMapper oauthMapper = oauthMappers.convertToOauthMapper(oauthType);
        oauthMapper.getOauthUser(code);
    }
}
