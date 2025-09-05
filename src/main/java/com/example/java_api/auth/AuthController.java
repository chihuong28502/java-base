package com.example.java_api.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_api.auth.dto.request.LoginRequest;
import com.example.java_api.auth.dto.request.RegisterRequest;
import com.example.java_api.auth.dto.response.LoginResponse;
import com.example.java_api.auth.dto.response.RefreshTokenReponse;
import com.example.java_api.auth.dto.response.RegisterResponse;
import com.example.java_api.common.exeptions.exeptionMulti.NotFoundException;
import com.example.java_api.configuration.getenv.EnvironmentConfiguration;
import com.example.java_api.configuration.getenv.JWTConfiguration;
import com.example.java_api.models.User;
import com.example.java_api.security.JwtTokenProvider;
import com.example.java_api.user.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final JWTConfiguration jwtConfiguration;
  private final EnvironmentConfiguration environmentConfiguration;
  private final AuthService authService;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public RegisterResponse register(@Validated @RequestBody RegisterRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  public LoginResponse login(@Validated @RequestBody LoginRequest request, HttpServletResponse response) {
    LoginResponse loginResponse = authService.login(request);
    ResponseCookie cookie = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
        .httpOnly(true)
        .secure(environmentConfiguration.getNodeEnv().equals("production"))
        .sameSite(environmentConfiguration.getNodeEnv().equals("production") ? "none" : "lax")
        .maxAge(jwtConfiguration.getExpirationRefreshToken())
        .path("/").build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    return loginResponse;
  }

  @PostMapping("/refresh")
  public RefreshTokenReponse refreshToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("refreshToken".equals(cookie.getName())) {
          String refreshToken = cookie.getValue();
          if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String userId = jwtTokenProvider.getUserIdFromJWT(refreshToken);
            User user = userService.getById(userId);
            String newAccessToken = jwtTokenProvider.generateAccessToken(user);
            return new RefreshTokenReponse(newAccessToken);
          }
        }
      }
    }
    throw new NotFoundException("Refresh token not found");
  }
}
