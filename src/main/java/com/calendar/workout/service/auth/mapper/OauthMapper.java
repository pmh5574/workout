package com.calendar.workout.service.auth.mapper;

import com.calendar.workout.dto.auth.OauthUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OauthMapper {

    RestTemplate restTemplate = new RestTemplate();

    boolean convertToOauthType(String oauthType);

    OauthUserInfo getOauthUser(String code);
}
