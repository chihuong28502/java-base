package com.example.java_api.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.java_api.user.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        com.example.java_api.models.User user = userRepository.findByEmail(usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new User(user.getEmail(), user.getPasswordHash(), Collections.singleton(authority));
    }

    @Override
    public UserDetails loadUserById(String id) {
        com.example.java_api.models.User user = userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new User(user.getEmail(), user.getPasswordHash(), Collections.singleton(authority));
    }
}


