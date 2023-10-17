package com.calendar.workout.service.auth.mapper;

import org.springframework.web.client.RestTemplate;

public interface OauthMapper {

    RestTemplate restTemplate = new RestTemplate();

    boolean convertToOauthType(String oauthType);

    void getOauthUser(String code);
}
