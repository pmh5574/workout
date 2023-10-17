package com.calendar.workout.web.auth;

import com.calendar.workout.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void login(@PathVariable String oauthType, @RequestParam("code") String code) {
        // todo 추후 프론트로 빼주고 PostMapping으로 변경 예정

        authService.login(oauthType, code);
    }

}
