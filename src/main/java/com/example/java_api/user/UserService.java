package com.example.java_api.user;

import org.springframework.stereotype.Service;

import com.example.java_api.common.context.MessageContext;
import com.example.java_api.common.exeptions.exeptionMulti.NotFoundException;
import com.example.java_api.models.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getById(String id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(MessageContext.getMessage("user.not.found")));
  }
}
