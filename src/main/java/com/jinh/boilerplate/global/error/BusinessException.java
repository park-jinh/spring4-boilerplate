package com.jinh.boilerplate.global.error;

import lombok.Getter;

/**
 * 프로젝트의 모든 비즈니스 예외(Business Exception)를 처리하는 최상위 클래스
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
