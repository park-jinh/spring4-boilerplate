package com.jinh.boilerplate.domain.health.api;

import com.jinh.boilerplate.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서버 상태 확인용 헬스 체크 컨트롤러 (Health Check Controller)
 * 세팅한 ApiResponse와 GlobalExceptionHandler의 동작을 검증합니다.
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthCheckController {

    /**
     * 1. 정상 응답 테스트
     * ResponseEntity를 사용하여 HTTP 200 OK 상태 코드를 명시적으로 반환합니다.
     * 접속 URL: http://localhost:8080/api/v1/health
     */
    @GetMapping
    public ResponseEntity<ApiResponse<String>> checkHealth() {
        // ResponseEntity.ok()를 통해 상태 코드와 바디를 함께 전달합니다.
        return ResponseEntity.ok(ApiResponse.ok("서버가 정상적으로 가동 중입니다 (API Boilerplate v1.0)"));
    }

    /**
     * 2. 예외 처리(Exception Handling) 테스트
     * 고의로 예외를 발생시켜 GlobalExceptionHandler가 ResponseEntity를 반환하는지 확인합니다.
     * 접속 URL: http://localhost:8080/api/v1/health/error
     */
    @GetMapping("/error")
    public ResponseEntity<ApiResponse<Void>> checkError() {
        // 비즈니스 로직 중 예외가 발생하면 핸들러에서 ResponseEntity<ApiResponse>를 반환하게 됩니다.
        throw new RuntimeException("테스트용 강제 에러가 발생했습니다!");
    }
}
