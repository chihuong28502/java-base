package com.example.java_api.auth.dto.response;

import com.example.java_api.common.dto.ResponseData;
import com.example.java_api.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends ResponseData {
  private String accessToken;

  @JsonIgnore
  private String refreshToken;
  private User user;
}


