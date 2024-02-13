package com.calendar.workout.domain.member;

import com.calendar.workout.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Builder
    public Member(String name, String email, String oauthId) {
        this.name = name;
        this.email = email;
        this.oauthId = oauthId;
    }
}
