package com.calendar.workout.common.interceptor;

import com.calendar.workout.exception.UnauthorizedException;
import com.calendar.workout.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String BEARER = "Bearer ";

    private final AuthService authService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        if (!(handler instanceof final HandlerMethod handlerMethod)) {
            return true;
        }

        if (handlerMethod.hasMethodAnnotation(Authenticated.class)) {

            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            checkHeader(authorizationHeader);

            final String token = authorizationHeader.substring(BEARER.length());
            checkTokenCertify(token);
        }
        return true;
    }

    private void checkHeader(final String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            throw new UnauthorizedException();
        }
    }

    private void checkTokenCertify(final String token) {
        if (!authService.isCertified(token)) {
            throw new UnauthorizedException();
        }
    }
}
