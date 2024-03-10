package ru.monitoring.user.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.monitoring.user.model.User;
import ru.monitoring.user.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserServiceImpl userService;

    public User getByUsername(String username) {
        return userService.getByUserEmail(username);
    }

    public User create(User user) {
        return userService.save(user);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(email);
    }

}
