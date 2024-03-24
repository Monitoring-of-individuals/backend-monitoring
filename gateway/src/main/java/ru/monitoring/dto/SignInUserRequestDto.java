package ru.monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Запрос на аутентификацию")
public class SignInUserRequestDto {

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Size(min = 5, max = 50, message = "Длина электронной почты должна быть от 5 до 50 символов")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Некорректный e-mail")
    @Schema(description = "Адрес электронной почты", example = "joncon@gmail.com")
    private String email;
    @Pattern(
            regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$")
    @NotBlank
    @Size(min = 6, max = 50, message = "Длина пароля от 6 до 50 символов")
    @Schema(description = "Пароль", example = "my1Pass^")
    private String password;
}
