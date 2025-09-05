package com.example.java_api.common.dto;

import java.io.Serializable;

import org.springframework.web.ErrorResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

  private T data;
  private ErrorResponse error;
  private String message;

  public ApiResponse(T data, String message) {
    this.data = data;
    this.message = message;
  }

  public ApiResponse(ErrorResponse error) {
    this.error = error;
  }

  @JsonProperty("success")
  public boolean isSuccess() {
    return error == null;
  }
}
