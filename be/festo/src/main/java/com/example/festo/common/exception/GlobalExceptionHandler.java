package com.example.festo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = { CustomNoSuchException.class })
    protected ResponseEntity<ErrorResponseEntity> handleCustomNoSuchException(CustomNoSuchException e) {
        log.error("handleCustomNoSuchException throw CustomNoSuchException : {}", e.getErrorCode());
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = { CustomIsPresentException.class })
    protected ResponseEntity<ErrorResponseEntity> handleCustomIsPresentException(CustomIsPresentException e) {
        log.error("handleCustomIsPresentException throw CustomIsPresentException : {}", e.getErrorCode());
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("handleCustomException throw CustomNoSuchException : {}"+ ex.getMessage());
        Map<String, Object> body = new HashMap<>();

        body.put("status", HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("message", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
}

}
