package com.app.calendar.user;

import com.app.calendar.user.User;
import com.app.calendar.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}