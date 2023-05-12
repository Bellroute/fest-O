package com.example.festo.member.application.service;

import com.example.festo.global.auth.JwtUtil;
import com.example.festo.member.adapter.in.web.model.LoginResponse;
import com.example.festo.member.application.port.in.LoginUseCase;
import com.example.festo.member.application.port.out.JoinMemberPort;
import com.example.festo.member.application.port.out.LoadMemberPort;
import com.example.festo.member.domain.AuthId;
import com.example.festo.member.domain.Member;
import com.example.festo.member.domain.ProfileImageUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final LoadMemberPort loadMemberPort;

    private final JoinMemberPort joinMemberPort;

    private final JwtUtil jwtUtil;

    public LoginResponse login(Long authId, String nickname, String profileUrl) {

        Member member;
        Long memberId;
        try {
            member = loadMemberPort.loadMember(AuthId.of(authId));
            memberId = member.getId();
        } catch (NoSuchElementException e) {
            member = Member.builder()
                           .authId(AuthId.of(authId))
                           .nickname(nickname)
                           .profileImageUrl(ProfileImageUrl.of(profileUrl))
                           .build();

            memberId = joinMemberPort.join(member);
        }

        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = jwtUtil.createRefreshToken(member.getId());

        return new LoginResponse(accessToken, refreshToken, memberId);
    }
}
