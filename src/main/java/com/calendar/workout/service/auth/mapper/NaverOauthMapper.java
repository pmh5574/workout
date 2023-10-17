package com.calendar.workout.service.auth.mapper;

public class NaverOauthMapper {

    private final String OAUTH_NAME = "naver";


    public boolean convertToOauthType(String oauthType) {
        return OAUTH_NAME.equals(oauthType);
    }
}
