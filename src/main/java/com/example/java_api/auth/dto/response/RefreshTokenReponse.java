package com.example.java_api.auth.dto.response;

import com.example.java_api.common.dto.ResponseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RefreshTokenReponse extends ResponseData {
  private String accessToken;
}


