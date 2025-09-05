package com.example.java_api.common.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.java_api.common.context.MessageContext;
import com.example.java_api.common.dto.ApiResponse;

@RestControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true; // áp dụng cho tất cả controller
  }

  @Override
  public Object beforeBodyWrite(
      Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {

    String message = MessageContext.getMessage();
    MessageContext.clear();

    // Nếu body đã là ApiResponse thì không bọc thêm
    if (body instanceof ApiResponse) {
      return body;
    }

    // Nếu là ErrorResponse (exception handler return)
    if (body instanceof ErrorResponse errorResponse) {
      return new ApiResponse<>(errorResponse);
    }

    // Nếu service có set message thì ưu tiên message đó
    if (message != null) {
      return new ApiResponse<>(body, message);
    }

    // Default: message "Success"
    return new ApiResponse<>(body, "Success");
  }
}
