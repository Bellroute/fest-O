package com.example.festo.member.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "[Request] authId는 빈 값일 수 없습니다.")
    private Long authId;

    @NotBlank(message = "[Request] nickname는 빈 값일 수 없습니다.")
    private String nickname;

    @NotBlank(message = "[Request] profileImageUrl는 빈 값일 수 없습니다.")
    private String profileImageUrl;

    // TODO 프론트 쪽 배포 후 주석 풀기
//    @NotBlank(message = "[Request] FCM 토큰은 빈 값일 수 없습니다.")
    private String fcmDeviceToken;

    @Builder
    public LoginRequest(Long authId, String nickname, String profileImageUrl, String fcmDeviceToken) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.fcmDeviceToken = fcmDeviceToken;
    }
}
