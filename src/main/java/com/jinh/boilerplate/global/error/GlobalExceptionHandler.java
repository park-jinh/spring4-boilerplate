package com.jinh.boilerplate.global.error;

import com.jinh.boilerplate.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 전역 예외 처리(Global Exception Handling) 설정
 * 계층화된 커스텀 예외들을 분류하여 처리합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. @Valid 또는 @Validated 바인딩 에러 처리
     * 주로 DTO의 유효성 검사 실패 시 발생합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("유효성 검사 실패: {}", e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(ApiResponse.fail(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /**
     * 2. 지원하지 않는 HTTP method 호출 시 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("지원하지 않는 메서드 호출: {}", e.getMethod());
        return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiResponse.fail(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
    }

    /**
     * 3. 존재하지 않는 정적 리소스 요청 시 발생 (favicon.ico, .well-known 등)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException e) {
        // 브라우저의 자동 요청이 많으므로 ERROR가 아닌 WARN으로 로그 레벨을 낮추어 관리합니다.
        log.warn("No static resource found for path: {}", e.getResourcePath());

        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.getStatus())
                .body(ApiResponse.fail(ErrorCode.RESOURCE_NOT_FOUND.getMessage()));
    }

    /**
     * 4. 비즈니스 로직 예외 처리 (Custom Exceptions 포함)
     * EntityNotFoundException, InvalidValueException 등이 모두 이 핸들러를 탑니다.
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("비즈니스 예외 발생 - 코드: {}, 메시지: {}", e.getErrorCode().getCode(), e.getMessage());

        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getMessage()));
    }

    /**
     * 5. 그 외 모든 최상위 예외 처리
     * 시스템 런타임 오류로 간주하고 500 에러를 반환합니다.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
        log.error("미처리 예외(Internal Server Error) 발생: ", e);
        return ResponseEntity.internalServerError()
                .body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}