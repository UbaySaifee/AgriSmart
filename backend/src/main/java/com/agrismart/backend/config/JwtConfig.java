package com.agrismart.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKeySpec jwtSecretKey(
        @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}") String secret
    ) {
        return new SecretKeySpec(
            secret.getBytes(java.nio.charset.StandardCharsets.UTF_8),
            "HmacSHA256"
        );
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKeySpec secretKey) {
        return NimbusJwtEncoder.withSecretKey(secretKey).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKeySpec secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(
            org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256
        ).build();
    }
}
