package com.example.java_api.configuration.getenv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.ToString;

@Getter
@Configuration
@ToString
public class EnvironmentConfiguration {

  @Value("${application.security.environment.nodeEnv}")
  private String nodeEnv;
}
