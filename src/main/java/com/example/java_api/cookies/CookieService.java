package com.example.java_api.cookies;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.example.java_api.configuration.getenv.EnvironmentConfiguration;
import com.example.java_api.configuration.getenv.JWTConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CookieService {

  private final EnvironmentConfiguration environmentConfiguration;
  private final JWTConfiguration jwtConfiguration;
  private final String path = "/";
  private final boolean httpOnly = true;

  public boolean isProduction() {
    return environmentConfiguration.getNodeEnv().equals("production");
  }

  public String getSameSite() {
    return environmentConfiguration.getNodeEnv().equals("production") ? "none" : "lax";
  }

  public void createCookie(String name, String value, long maxAgeSeconds, HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie.from(name, value)
        .httpOnly(httpOnly)
        .secure(isProduction())
        .sameSite(getSameSite())
        .path(path)
        .maxAge(maxAgeSeconds)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public void clearCookie(String name, HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie.from(name, "")
        .httpOnly(httpOnly)
        .secure(isProduction())
        .sameSite(getSameSite())
        .path(path)
        .maxAge(0)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
    createCookie("refreshToken", refreshToken,
        jwtConfiguration.getExpirationRefreshToken(), response);
  }

  public void clearRefreshTokenCookie(HttpServletResponse response) {
    clearCookie("refreshToken", response);
  }
}
