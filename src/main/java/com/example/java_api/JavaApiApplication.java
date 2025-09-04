package com.example.java_api;

import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.java_api.configuration.JWTConfiguration;

@SpringBootApplication
public class JavaApiApplication implements CommandLineRunner {
  private final JWTConfiguration jwtConfiguration;

  public JavaApiApplication(JWTConfiguration jwtConfiguration) {
    this.jwtConfiguration = jwtConfiguration;
  }

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    SpringApplication.run(JavaApiApplication.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println("Hello World");
    System.out.println(jwtConfiguration.getSecretAccessToken());
  }

}
