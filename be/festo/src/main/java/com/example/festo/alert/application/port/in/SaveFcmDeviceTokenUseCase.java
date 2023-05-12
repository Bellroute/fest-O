package com.example.festo.alert.application.port.in;

public interface SaveFcmDeviceTokenUseCase {

    void save(Long memberId, String token);
}
