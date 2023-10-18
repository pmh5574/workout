package com.calendar.workout.service.auth.mapper;

import com.calendar.workout.dto.auth.response.GoogleUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OauthMapper {

    RestTemplate restTemplate = new RestTemplate();

    boolean convertToOauthType(String oauthType);

    GoogleUserInfo getOauthUser(String code);
}
