package com.jinh.boilerplate.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jasypt 설정 클래스
 * 외부 시스템 프로퍼티나 환경 변수로부터 마스터 키를 주입받으며, 누락 시 기본값을 할당합니다.
 */
@Slf4j
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    /*
     * [Jasypt Master Key 주입 우선순위 전략]
     * * 1순위: OS 환경 변수 (Environment Variable) - 가장 안전함
     * 리눅스 서버의 /etc/environment 또는 .bashrc 등에 'JASYPT_PASSWORD' 등록
     * Spring은 대문자/언더바 형태의 환경 변수를 소문자/점 형태의 프로퍼티로 자동 바인딩함
     * * 2순위: JVM 시스템 프로퍼티 (System Properties) - 편리함
     * 실행 시 -Djasypt.password=내비밀키 옵션으로 주입
     * 프로세스 목록(ps -ef)에서 키가 노출될 위험이 있으므로 주의 필요
     * * 3순위: 설정 파일 (application.yml) - 보안상 취약 (비권장)
     * yml 내부에 jasypt.password: 내비밀키 명시
     * Git에 키가 직접 노출되므로 권장하지 않으며, 로컬 개발 테스트 시에만 제한적 사용
     * * 4순위: 코드 내 기본값 (Fallback)
     * 모든 주입이 실패했을 때 사용하는 최후의 수단
     */
    @Value("${jasypt.password:#{null}}")
    private String masterKey;

    // 외부 주입 키가 없을 경우 사용할 기본값 (실 운영 환경에서는 반드시 외부 주입 권장)
    private static final String DEFAULT_KEY = "spring4_default_secret_key";


    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        // 1. 키 주입 로직: 환경 변수/VM 옵션 우선 -> 없을 경우 필드값 -> 마지막으로 기본값 사용
        String finalKey = (masterKey != null && !masterKey.isEmpty()) ? masterKey : DEFAULT_KEY;

        if (finalKey.equals(DEFAULT_KEY)) {
            log.warn("[Security] Jasypt 마스터 키가 감지되지 않아 기본 키를 사용합니다. 운영 환경에서는 보안에 유의하십시오.");
        }

        config.setPassword(finalKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        return encryptor;
    }

    /**
     * 개발 단계에서 암호화된 값을 생성하거나 복호화 결과를 확인하기 위한 간단한 예시 코드입니다.
     * 필요 시 별도의 Test 코드로 분리하여 사용하는 것을 권장합니다.
     */
    public String encrypt(String plainText) {
        return stringEncryptor().encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return stringEncryptor().decrypt(encryptedText);
    }
}