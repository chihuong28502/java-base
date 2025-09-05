package com.example.java_api.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.java_api.configuration.getenv.JWTConfiguration;
import com.example.java_api.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
  private final JWTConfiguration jwtConfiguration;

  // Tạo token từ userId
  public String generateAccessToken(User user) {
    Date now = new Date();
    long expirationInMs = jwtConfiguration.getExpirationAccessToken();
    Date expiryDate = new Date(now.getTime() + expirationInMs);
    SecretKey key = Keys.hmacShaKeyFor(jwtConfiguration.getSecretAccessToken().getBytes(StandardCharsets.UTF_8));

    return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .claim("username", user.getFullName())
        .claim("email", user.getEmail())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(key, Jwts.SIG.HS512)
        .compact();
  }


  public String generateRefreshToken(User user) {
    Date now = new Date();
    long expirationInMs = jwtConfiguration.getExpirationRefreshToken();
    Date expiryDate = new Date(now.getTime() + expirationInMs);
    SecretKey key = Keys.hmacShaKeyFor(jwtConfiguration.getSecretRefreshToken().getBytes(StandardCharsets.UTF_8));

    return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(key, Jwts.SIG.HS512)
        .compact();
  }

  public String getUserIdFromJWT(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtConfiguration.getSecretAccessToken().getBytes(StandardCharsets.UTF_8));
    Claims claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getSubject();
  }

  // Validate token
  public boolean validateToken(String authToken, HttpServletRequest request) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtConfiguration.getSecretAccessToken().getBytes(StandardCharsets.UTF_8));
      Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
      return true;
    } catch (SignatureException ex) {
      LOGGER.error("Invalid JWT signature");
      request.setAttribute("jwt_error", "Chữ ký JWT không hợp lệ");
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid JWT token");
      request.setAttribute("jwt_error", "Token không hợp lệ");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired JWT token");
      request.setAttribute("jwt_error", "Token hết hạn");
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("Unsupported JWT token");
      request.setAttribute("jwt_error", "Token không hợp lệ");
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty");
      request.setAttribute("jwt_error", "Token không hợp lệ");
    }
    return false;
  }


  public boolean validateRefreshToken(String authToken) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtConfiguration.getSecretRefreshToken().getBytes(StandardCharsets.UTF_8));
      Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
      return true;
    } catch (SignatureException ex) {
      LOGGER.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty");
    }
    return false;
  }
}
