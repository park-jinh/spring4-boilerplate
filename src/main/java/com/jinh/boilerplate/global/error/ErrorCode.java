package com.jinh.boilerplate.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 비즈니스 에러 코드(Error Code) 관리 Enum
 * 시스템 전반에서 발생하는 비즈니스 예외들을 정의합니다.
 */
@Getter
public enum ErrorCode {

    // Common (공통 에러)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C004", "정보를 찾을 수 없습니다."),

    // Member (회원 관련 도메인 에러 예시)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 이메일입니다."),

    // Resource (정적 리소스 관련 추가)
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", " 해당 경로의 리소스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}