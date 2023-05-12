package com.example.festo.member.application.port.out;

import com.example.festo.alert.domain.FcmDeviceToken;

public interface SaveFcmDeviceTokenPort {

    void save(FcmDeviceToken fcmDeviceToken);
}
