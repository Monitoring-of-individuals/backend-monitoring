package ru.monitoring.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.monitoring.dto.UserResponseDto;

/**
 * Класс хранит ДТО пользователя и его токен авторизации
 * ТОЛЬКО ДЛЯ ВНУТРЕННЕГО ИСПОЛЬЗОВАНИЯ!
 */
@Builder
@Setter
@Getter
public class UserDtoWithTokenInternal {
    private UserResponseDto userResponseDto;
    private String token;
}
