package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.member.application.port.out.SaveFcmDeviceTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmDeviceTokenAdapter implements SaveFcmDeviceTokenPort {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;

    @Override
    public void save(FcmDeviceToken fcmDeviceToken) {
        fcmDeviceTokenRepository.save(new FcmDeviceTokenEntity(fcmDeviceToken.getMemberId(), fcmDeviceToken.getToken()));
    }
}
