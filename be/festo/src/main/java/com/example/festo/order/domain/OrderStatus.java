package com.example.festo.order.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {

    WAITING_ACCEPTANCE("주문 수락 대기", 0, "새로운 주문이 들어왔어요!"),
    PREPARING_ORDER("상품 준비중", 1, "주문이 수락되었어요! 금방 준비할게요:)"),
    WAITING_RECEIVE("수령 대기", 2, "상품이 준비되었어요!"),
    COMPLETE("거래 완료", 3, "상품 수령이 완료되었습니다!"),

    CANCELED("취소", -1, "주문이 취소되었습니다.");

    private final String title;
    private final int number;

    private final String message;

    OrderStatus(final String title, final int number, final String message) {
        this.title = title;
        this.number = number;
        this.message = message;
    }

    public static OrderStatus findBy(String value) {
        return Arrays.stream(values())
                .filter(status -> status.name()
                        .equals(value.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("연산자를 찾을 수 없습니다."));
    }

    public boolean before(OrderStatus status) {
        return this.number + 1 == status.number;
    }
}