package ru.monitoring.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "Ответ на запрос на авторизацию")
public class UserResponseDto {
    @Schema(description = "ID пользователя", example = "1")
    private long id;
    @Schema(description = "Имя пользователя", example = "Jon")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Conor")
    private String lastName;
    @Schema(description = "Адрес электронной почты", example = "joncon@gmail.com")
    private String email;
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private String token;
}
