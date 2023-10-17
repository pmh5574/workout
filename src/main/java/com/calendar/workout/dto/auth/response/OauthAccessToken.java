package com.calendar.workout.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthAccessToken(
        @JsonProperty("access_token") String accessToken
        , @JsonProperty("expires_in") String expiresIn
        , @JsonProperty("refresh_token") String refreshToken // 추후 사용시 access_type HTTP 쿼리 매개변수를 offline 로 설정 https://developers.google.com/identity/protocols/oauth2/web-server
        , String scope
        , @JsonProperty("token_type") String tokenType) {

}
