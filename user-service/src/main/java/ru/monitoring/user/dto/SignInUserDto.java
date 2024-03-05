package ru.monitoring.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Запрос на аутентификацию")
public class SignInUserDto {

    @NotBlank
    @Email
    @Schema(description = "Почта пользователя", example = "joncon@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Пароль", example = "my1Pass^")
    private String password;
}
