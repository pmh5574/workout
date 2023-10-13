package com.calendar.workout.domain.auth;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @Column
    private String refreshToken;

    public RefreshToken(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
