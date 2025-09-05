package com.example.java_api.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest  {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;
}


