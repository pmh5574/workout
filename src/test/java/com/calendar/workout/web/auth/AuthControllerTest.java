package com.calendar.workout.web.auth;

import com.calendar.workout.dto.auth.AuthTokens;
import com.calendar.workout.dto.auth.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String REFRESH_TOKEN = "refreshToken";
    private final static String ACCESS_TOKEN = "accessToken";

    @DisplayName("로그인")
    @Test
    void login() {
        // given
        LoginRequest loginRequest = new LoginRequest("code");
        AuthTokens authTokens = AuthTokens.builder()
                .accessToken(ACCESS_TOKEN)
                .refreshToken(REFRESH_TOKEN)
                .build();
        // git commit test
        // when

        // then
    }

    void test() {

    }
}