package com.example.festo.order.application.service;

import com.example.festo.order.domain.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusChangedEventHandler {

    @Async
    @EventListener(OrderStatusChangedEvent.class)
    public void handle(OrderStatusChangedEvent event) throws InterruptedException {
        Thread.sleep(2000);




        //order에서 상태값이 변경되는 경우에 이벤트발생 - 완
        //envent에 넘어와야할 데이터 -> OrderStatusChangedEvent
        //getMemberTokenOutPort -> ownerId, ordererId

        //status확인해 보내야할 사람이 2인경우는 FirebaseService.sendMessageTo(ownerId, 제목, 내용);

        // TODO 알림 메시지 db에 저장하기 -> 아웃포트 알림목록 DB저장

        String status = event.getOrderStatus();

        if (status.equals("WAITING_ACCEPTANCE")) { // 부스 관리자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getBoothOwnerId(), event.getOrderStatus());
            //로직 ownerId memberId
        } else if (status.equals("PREPARING_ORDER") || status.equals("WAITING_RECEIVE")) { // 주문자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId(), event.getOrderStatus());
        } else { // 부스 관리자와 주문자 모두에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId() + " " + event.getBoothOwnerId(), event.getOrderStatus());
        }

    }
}
