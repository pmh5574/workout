package com.calendar.workout.service.auth.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OauthMappers {

    private final List<OauthMapper> oauthMapperList;

    public OauthMappers(List<OauthMapper> oauthMapperList) {
        this.oauthMapperList = oauthMapperList;
    }

    public OauthMapper convertToOauthMapper(String oauthType) {
        return this.oauthMapperList.stream()
                .filter(oauthMapper -> oauthMapper.convertToOauthType(oauthType))
                .findFirst()
                .orElseThrow();
    }
}
