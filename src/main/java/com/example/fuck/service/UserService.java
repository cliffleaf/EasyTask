package com.example.fuck.service;

import com.example.fuck.model.User;
import com.example.fuck.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}