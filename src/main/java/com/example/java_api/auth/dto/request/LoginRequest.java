package com.example.java_api.auth.dto.request;

import com.example.java_api.common.context.MessageContext;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequest  {

  @NotBlank
  @Email(message = "{field.email.invalid}")
  private String email;
  
  @NotBlank
  @Size(min = 8, message = "{field.min.length}")
  private String password;
  
}


