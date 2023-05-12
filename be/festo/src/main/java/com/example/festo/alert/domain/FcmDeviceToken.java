package com.example.festo.alert.domain;

import lombok.Getter;

@Getter
public class FcmDeviceToken {

    private final Long memberId;

    private final String token;

    public FcmDeviceToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
