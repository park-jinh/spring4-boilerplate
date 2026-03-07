package com.jinh.boilerplate.global.error;

/**
 * 유효하지 않은 입력값일 때 발생하는 예외 (400 Bad Request)
 */
public class InvalidValueException extends BusinessException {

    public InvalidValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
