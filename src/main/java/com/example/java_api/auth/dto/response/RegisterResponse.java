package com.example.java_api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {
  private String id;
  private String fullName;
  private String email;
}


