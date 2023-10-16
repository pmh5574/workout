package com.calendar.workout.web.auth;

import com.calendar.workout.dto.auth.request.LoginRequest;
import com.calendar.workout.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/{oauthType}")
    public ResponseEntity<AuthResponse> login(@PathVariable String oauthType, @RequestBody @Valid LoginRequest loginRequest) {
        LoginTokens loginTokens = authService.login(oauthType, loginRequest);
    }

}
