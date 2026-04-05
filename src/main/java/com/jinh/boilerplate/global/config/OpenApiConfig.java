package com.jinh.boilerplate.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Swagger (Springdoc OpenAPI) 설정 클래스
 */
@Configuration
public class OpenApiConfig {

    @Bean
    @Profile({"local", "dev"}) // local 및 dev 프로필에서만 Swagger Bean을 생성합니다.
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot 4.0 API Boilerplate")
                        .description("Java 21 및 Spring Boot 4.0 기반의 API 표준 보일러플레이트 명세서입니다.")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("개발용 서버")

                ));
    }
}
