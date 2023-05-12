package com.example.festo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    S3_EXCEPTION(HttpStatus.FORBIDDEN,"S3관련 Exception 입니다."),
    NO_ROLE(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    FAIL_VALID(HttpStatus.BAD_REQUEST, "빈 값으로 들어왔습니다."),
    NO_SUCH_ELEMENT(HttpStatus.BAD_REQUEST, "값을 찾을 수 없습니다."),
    BOOTH_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 부스를 찾을 수 없습니다."),
    FESTIVAL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 축제를 찾을 수 없습니다."),
    INVITE_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 코드에 맞는 축제를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 맴버를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    FESTIVAL_IS_PRESENT(HttpStatus.FORBIDDEN, "이미 개설한 축제가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
