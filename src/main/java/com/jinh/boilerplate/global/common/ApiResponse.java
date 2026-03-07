package com.jinh.boilerplate.global.common;

/**
 * REST API 전용 공통 응답(Response) 규격
 * Java 21의 불변 객체(Record)를 활용하여 데이터를 전달합니다.
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    // 1. 성공 응답 (기본 메시지 + 데이터 포함)
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", data);
    }

    // 2. 성공 응답 (사용자 정의 메시지 + 데이터 포함)
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 3. 성공 응답 (기본 메시지 + 데이터 없음)
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", null);
    }

    // 4. 성공 응답 (사용자 정의 메시지 + 데이터 없음)
    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // 5. 실패 응답 (에러 메시지 포함)
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
