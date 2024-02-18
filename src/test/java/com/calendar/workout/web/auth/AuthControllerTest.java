package com.calendar.workout.web.auth;

import com.calendar.workout.dto.auth.AuthTokens;
import com.calendar.workout.dto.auth.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    private final static String REFRESH_TOKEN = "refreshToken";
    private final static String ACCESS_TOKEN = "accessToken";
    private final static String TYPE_GOOGLE = "google";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("로그인")
    @Test
    void test() throws Exception {
        //todo 인증 테스트 해야함

        // given
        LoginRequest loginRequest = new LoginRequest("code");
        AuthTokens authTokens = AuthTokens.builder()
                .accessToken(ACCESS_TOKEN)
                .refreshToken(REFRESH_TOKEN)
                .build();

        // when
        String json = objectMapper.writeValueAsString(loginRequest);

        // then
        ResultActions resultActions = mockMvc.perform(post("/api/auth/{oauthType}", TYPE_GOOGLE)
                        .contentType(APPLICATION_JSON)
                        .content(json));
    }
}