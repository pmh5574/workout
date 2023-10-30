package com.calendar.workout.service.auth;

import com.calendar.workout.domain.auth.RefreshToken;
import com.calendar.workout.domain.auth.RefreshTokenRepository;
import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
import com.calendar.workout.dto.auth.AuthTokens;
import com.calendar.workout.dto.auth.OauthUserInfo;
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

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public boolean isCertified(String token) {
        return false;
    }

    @Transactional
    public AuthTokens login(String oauthType, String code) {
        OauthMapper oauthMapper = oauthMappers.convertToOauthMapper(oauthType);
        OauthUserInfo oauthUserInfo = oauthMapper.getOauthUser(code);

        Member member = findOrSaveMember(oauthUserInfo);

        String refreshToken = jwtTokenProvider.createRefreshToken();
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));

        RefreshToken saveRefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .member(member)
                .build();
        refreshTokenRepository.save(saveRefreshToken);
        return AuthTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Transactional
    public Member findOrSaveMember(OauthUserInfo oauthUserInfo) {
        return memberRepository.findByOauthId(oauthUserInfo.getId())
                .orElseGet(() -> saveMember(oauthUserInfo));
    }

    @Transactional
    public Member saveMember(OauthUserInfo oauthUserInfo) {
        Member member = Member.builder()
                .name(oauthUserInfo.getName())
                .email(oauthUserInfo.getEmail())
                .oauthId(oauthUserInfo.getId())
                .build();

        return memberRepository.save(member);
    }
}
