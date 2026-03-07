package com.jinh.boilerplate.global.error;

/**
 * 리소스를 찾지 못했을 때 발생하는 예외 (404 Not Found)
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND); // 기본 에러 코드를 지정하거나 상황에 맞게 주입
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND); // 기본 에러 코드를 지정하거나 상황에 맞게 주입
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
