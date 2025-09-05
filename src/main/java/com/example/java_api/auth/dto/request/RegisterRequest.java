package com.example.java_api.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

  @NotBlank(message = "Full name không được để trống")
  @Size(max = 100)
  private String fullName;

  @NotBlank
  @Email(message = "Email không hợp lệ")
  @Size(max = 255)
  private String email;

  @NotBlank
  @Size(min = 6, max = 100, message = "Mật khẩu phải có ít nhất 6 ký tự")
  private String password;
}


