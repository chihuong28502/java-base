package com.example.java_api.common.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.java_api.common.dto.ApiResponse;
import com.example.java_api.common.exeptions.exeptionMulti.AuthenticationException;
import com.example.java_api.common.exeptions.exeptionMulti.ConflictException;
import com.example.java_api.common.exeptions.exeptionMulti.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
    ApiResponse<Object> response = new ApiResponse<>();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ApiResponse<Object>> handleConflict(ConflictException ex) {
    ApiResponse<Object> response = new ApiResponse<>();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse<Object>> handleAuthentication(AuthenticationException ex) {
    ApiResponse<Object> response = new ApiResponse<>();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex) {
    ApiResponse<Object> response = new ApiResponse<>();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

}