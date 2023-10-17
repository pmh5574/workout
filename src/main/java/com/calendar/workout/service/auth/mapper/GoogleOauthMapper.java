package com.calendar.workout.service.auth.mapper;

import com.calendar.workout.dto.auth.response.OauthAccessToken;
import com.calendar.workout.exception.InvalidAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class GoogleOauthMapper implements OauthMapper {


    private final String OAUTH_NAME = "google";



    private final String clientId;

    private final String clientSecret;
    private final String redirectUri;
    private final String tokenUri;
    private final String userUri;

    public GoogleOauthMapper(
            @Value("${spring.oauth2.user.google.client-id}") String clientId,
            @Value("${spring.oauth2.user.google.client-secret}") String clientSecret,
            @Value("${spring.oauth2.user.google.redirect-uri}") String redirectUri,
            @Value("${spring.oauth2.provider.google.token-uri}") String tokenUri,
            @Value("${spring.oauth2.provider.google.user-info-uri}") String userUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenUri = tokenUri;
        this.userUri = userUri;
    }

    @Override
    public boolean convertToOauthType(String oauthType) {
        return OAUTH_NAME.equals(oauthType);
    }

    @Override
    public void getOauthUser(String code) {
        String accessToken = getAccessToken(code);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                userUri,
                HttpMethod.GET,
                userInfoRequestEntity,
                String.class
        );
        log.info("response == {}", response.getBody());
    }

    private String getAccessToken(String code) {

        Map<String, String> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OauthAccessToken> responseEntity = restTemplate.postForEntity(tokenUri, params, OauthAccessToken.class);

        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(InvalidAuthorizationException::new)
                .accessToken();
    }
}
