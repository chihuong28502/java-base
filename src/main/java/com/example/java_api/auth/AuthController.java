package com.example.java_api.auth;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
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
import com.example.java_api.common.context.MessageContext;
import com.example.java_api.common.exeptions.exeptionMulti.AuthenticationException;
import com.example.java_api.common.exeptions.exeptionMulti.NotFoundException;
import com.example.java_api.cookies.CookieService;
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

  private final AuthService authService;
  private final UserService userService;
  private final CookieService cookieService;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public RegisterResponse register(@Validated @RequestBody RegisterRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  public LoginResponse login(@Validated @RequestBody LoginRequest request, HttpServletResponse response) {
    LoginResponse loginResponse = authService.login(request);
    cookieService.setRefreshTokenCookie(loginResponse.getRefreshToken(), response);
    return loginResponse;
  }

  @PostMapping("/refresh")
  public RefreshTokenReponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null)
      throw new NotFoundException(MessageContext.getMessage("auth.token.notfound"));

    String refreshToken = Arrays.stream(cookies)
        .filter(cookie -> "refreshToken".equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElseThrow(() -> new NotFoundException(MessageContext.getMessage("auth.token.notfound")));
    if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
      cookieService.clearRefreshTokenCookie(response); // xoá luôn cookie cũ
      throw new AuthenticationException(MessageContext.getMessage("auth.token.invalid"));
    }
    String userId = jwtTokenProvider.getUserIdFromJWT(refreshToken);
    User user = userService.getById(userId);
    String newAccessToken = jwtTokenProvider.generateAccessToken(user);

    return new RefreshTokenReponse(newAccessToken);
  }

}
