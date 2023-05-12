package com.example.festo.alert.application.port.out;

import com.example.festo.alert.domain.FcmDeviceToken;

public interface LoadFcmDeviceTokenPort {

    FcmDeviceToken loadFcmDeviceTokenByToken(String token);
}
