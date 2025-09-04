package com.example.java_api.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.ToString;

@Getter
@Configuration
@ToString 
public class JWTConfiguration {

    @Value("${application.security.jwt.secretAccessToken}")
    private String secretAccessToken;

    @Value("${application.security.jwt.secretRefreshToken}")
    private String secretRefreshToken;

    @Value("${application.security.jwt.expirationAccessToken}")
    private long expirationAccessToken;

    @Value("${application.security.jwt.expirationRefreshToken}")
    private long expirationRefreshToken;
}
