package ru.monitoring.service.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.monitoring.model.User;
import ru.monitoring.service.user.impl.UserService;

/**
 * Сервис для работы с пользователями в контексте безопасности.
 * Обеспечивает доступ к данным пользователя посредством сервиса GateWayApp и интеграцию с Spring Security.
 */
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserService userService;

    /**
     * Получает данные пользователя по его email.
     *
     * @param userEmail Электронная почта пользователя.
     * @return Объект пользователя, связанный с данным email.
     */
    public User getByUserEmail(String userEmail) {
        return userService.getByUserEmail(userEmail);
    }

    /**
     * Создает нового пользователя в системе.
     *
     * @param user Объект пользователя для создания.
     * @return Созданный объект пользователя.
     */
    public User create(User user) {
        return userService.save(user);
    }

    /**
     * Возвращает сервис для работы с деталями пользователя, интегрированный с Spring Security.
     *
     * @return Сервис, предоставляющий информацию о пользователях.
     */
    public UserDetailsService userDetailsService() {
        return this::getByUserEmail; // Метод ссылка, возвращающий данные пользователя по email.
    }

    /**
     * Получает текущего аутентифицированного пользователя из контекста безопасности.
     *
     * @return Объект текущего пользователя.
     */
    public User getCurrentUser() {
        // Получение имени пользователя (в данном случае email) из контекста Spring Security
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return getByUserEmail(email); // Возвращает пользователя по извлеченному email
    }
}
