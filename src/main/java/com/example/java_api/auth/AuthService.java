package com.example.java_api.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_api.auth.dto.request.LoginRequest;
import com.example.java_api.auth.dto.request.RegisterRequest;
import com.example.java_api.auth.dto.response.LoginResponse;
import com.example.java_api.auth.dto.response.RegisterResponse;
import com.example.java_api.common.context.MessageContext;
import com.example.java_api.common.exeptions.exeptionMulti.AuthenticationException;
import com.example.java_api.common.exeptions.exeptionMulti.ConflictException;
import com.example.java_api.common.exeptions.exeptionMulti.NotFoundException;
import com.example.java_api.models.User;
import com.example.java_api.security.JwtTokenProvider;
import com.example.java_api.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public RegisterResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail()))
      throw new ConflictException(MessageContext.getMessage("auth.user.already.exists"));

    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail().toLowerCase());
    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

    User saved = userRepository.save(user);
    MessageContext.setMessage(MessageContext.getMessage("auth.register.success"));
    return new RegisterResponse(saved.getId(), saved.getFullName(), saved.getEmail());
  }

  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail().toLowerCase())
        .orElseThrow(() -> new NotFoundException(MessageContext.getMessage("auth.user.not.found")));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
      throw new AuthenticationException(MessageContext.getMessage("auth.invalid.credentials"));

    String token = jwtTokenProvider.generateAccessToken(user);
    String refreshToken = jwtTokenProvider.generateRefreshToken(user);
    MessageContext.setMessage(MessageContext.getMessage("auth.login.success"));
    return new LoginResponse(token, refreshToken, user);
  }
}
