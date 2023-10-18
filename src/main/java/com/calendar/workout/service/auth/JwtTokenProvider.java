package com.calendar.workout.service.auth;

import com.calendar.workout.domain.auth.RefreshToken;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final Long accessTokenValidityInSeconds;
    private final Long refreshTokenValidityInSeconds;

    public JwtTokenProvider(@Value("${jwt.secret-key}") SecretKey secretKey,
                            @Value("#{T(Long).parseLong('${jwt.access-expire-time}')}") Long accessTokenValidityInSeconds,
                            @Value("#{T(Long).parseLong('${jwt.refresh-expire-time}')}") Long refreshTokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public RefreshToken createRefreshToken(String time) {
        createToken(s)
    }

    public void createAccessToken(RefreshToken refreshToken) {
    }

    private String createToken(Long Time) {

        long now = new Date().getTime();
        Date validTime = new Date(now + validity);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validTime)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
