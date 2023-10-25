package com.calendar.workout.web.auth;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import com.calendar.workout.common.util.CookieHandler;
import com.calendar.workout.dto.auth.AuthTokens;
import com.calendar.workout.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/oauth2/callback/google")
    public void googleLogin(@RequestParam("code") String accessCode) {
        return ;
    }

    @GetMapping("/{oauthType}")
    public ResponseEntity<AuthTokens> login(@PathVariable String oauthType, @RequestParam("code") String code, HttpServletResponse response) {
        // todo 추후 프론트로 빼주고 PostMapping으로 변경 예정

        AuthTokens authTokens = authService.login(oauthType, code);
        ResponseCookie responseCookie = CookieHandler.getResponseCookie(authTokens.refreshToken());
        response.addHeader(SET_COOKIE, responseCookie.toString());

        return ResponseEntity.status(CREATED)
                .body(authTokens);
    }

}
