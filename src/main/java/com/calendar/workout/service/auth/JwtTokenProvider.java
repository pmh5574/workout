package com.calendar.workout.service.auth;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final Long accessTokenValiditySeconds;
    private final Long refreshTokenValiditySeconds;

    public JwtTokenProvider(@Value("${jwt.secret-key}") SecretKey secretKey,
                            @Value("#{T(Long).parseLong('${jwt.access-expire-time}')}") Long accessTokenValiditySeconds,
                            @Value("#{T(Long).parseLong('${jwt.refresh-expire-time}')}") Long refreshTokenValiditySeconds) {
        this.secretKey = secretKey;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String createRefreshToken(String subject) {
        return createToken(refreshTokenValiditySeconds, subject);
    }

    public String createAccessToken(String subject) {
        return createToken(accessTokenValiditySeconds, subject);
    }

    private String createToken(Long Time, String subject) {

        Date now = new Date();
        Date validTime = new Date(now.getTime() + Time);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validTime)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
