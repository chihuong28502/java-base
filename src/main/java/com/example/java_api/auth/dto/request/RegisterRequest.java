package com.example.java_api.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "{field.required}")
  @Size(max = 100)
  private String fullName;

  @NotBlank
  @Email(message = "{field.email.invalid}")
  @Size(max = 255)
  private String email;

  @NotBlank
  @Size(min = 6, max = 100, message = "{field.min.length}")
  private String password;
}
