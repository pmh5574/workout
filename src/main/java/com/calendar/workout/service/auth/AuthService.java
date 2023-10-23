package com.calendar.workout.service.auth;

import com.calendar.workout.domain.auth.RefreshToken;
import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
import com.calendar.workout.dto.auth.response.GoogleUserInfo;
import com.calendar.workout.service.auth.mapper.OauthMapper;
import com.calendar.workout.service.auth.mapper.OauthMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final OauthMappers oauthMappers;

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public boolean isCertified(String token) {
        return false;
    }

    @Transactional
    public void login(String oauthType, String code) {
        OauthMapper oauthMapper = oauthMappers.convertToOauthMapper(oauthType);
        GoogleUserInfo googleUserInfo = oauthMapper.getOauthUser(code);

        Member member = findOrSaveMember(googleUserInfo);

        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()));
        String accessToken = jwtTokenProvider.createAccessToken(refreshToken);
    }

    @Transactional
    public Member findOrSaveMember(GoogleUserInfo googleUserInfo) {
        return memberRepository.findByOauthId(googleUserInfo.id())
                .orElseGet(() -> saveMember(googleUserInfo));
    }

    @Transactional
    public Member saveMember(GoogleUserInfo googleUserInfo) {
        Member member = Member.builder()
                .name(googleUserInfo.name())
                .email(googleUserInfo.email())
                .oauthId(googleUserInfo.id())
                .build();

        return memberRepository.save(member);
    }
}
