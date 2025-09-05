package com.example.java_api.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_api.models.User;

public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}


