package com.example.festo.alert.application.service;

import com.example.festo.alert.application.port.in.SaveFcmDeviceTokenUseCase;
import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.member.application.port.out.SaveFcmDeviceTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService implements SaveFcmDeviceTokenUseCase {

    private final SaveFcmDeviceTokenPort saveFcmDeviceTokenPort;

    @Override
    public void save(Long memberId, String token) {
        saveFcmDeviceTokenPort.save(new FcmDeviceToken(memberId, token));
    }
}
