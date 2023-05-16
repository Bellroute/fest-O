package com.example.festo.order.application.service;

import com.example.festo.alert.application.port.in.SaveNotificationUseCase;
import com.example.festo.alert.application.port.out.LoadFcmDeviceTokenPort;
import com.example.festo.alert.application.service.FirebaseCloudMessageService;
import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.order.domain.OrderStatus;
import com.example.festo.order.domain.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusChangedEventHandler {

    private final LoadFcmDeviceTokenPort loadFcmDeviceTokenPort;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final SaveNotificationUseCase saveNotificationUseCase;

    @Async
    @EventListener(OrderStatusChangedEvent.class)
    public void handle(OrderStatusChangedEvent event) {
        List<FcmDeviceToken> boothOwnerToken = loadFcmDeviceTokenPort.loadFcmDeviceTokenByMemberId(event.getBoothOwnerId());
        List<FcmDeviceToken> ordererToken = loadFcmDeviceTokenPort.loadFcmDeviceTokenByMemberId(event.getOrdererId());

        OrderStatus status = OrderStatus.findBy(event.getOrderStatus());

        List<FcmDeviceToken> receivers = new ArrayList<>();
        if (status.equals(OrderStatus.WAITING_ACCEPTANCE)) { // 부스 관리자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getBoothOwnerId(), event.getOrderStatus());
            receivers.addAll(boothOwnerToken);
        } else if (status.equals(OrderStatus.PREPARING_ORDER) || status.equals(OrderStatus.WAITING_RECEIVE)) { // 주문자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId(), event.getOrderStatus());
            receivers.addAll(ordererToken);
        } else { // 부스 관리자와 주문자 모두에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId() + " " + event.getBoothOwnerId(), event.getOrderStatus());
            receivers.addAll(ordererToken);
            receivers.addAll(boothOwnerToken);
        }

        receivers.forEach(receiver -> {
            System.out.println(receiver.getMemberId() + " " + receiver.getToken());
            try {
                firebaseCloudMessageService.sendMessageTo(receiver.getToken(), status.getTitle(), status.getMessage());
                saveNotificationUseCase.saveNotification(receiver.getMemberId(), event.getOrderId(), event.getTimestamp());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
