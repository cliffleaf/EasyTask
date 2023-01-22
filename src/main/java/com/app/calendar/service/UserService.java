package com.app.calendar.service;

import com.app.calendar.model.User;
import com.app.calendar.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}