package com.example.festo.alert.application.port.out;

import com.example.festo.alert.domain.FcmDeviceToken;

import java.util.List;

public interface LoadFcmDeviceTokenPort {

    FcmDeviceToken loadFcmDeviceTokenByToken(String token);
    List<FcmDeviceToken> loadFcmDeviceTokenByMemberId(Long memberId);
}
