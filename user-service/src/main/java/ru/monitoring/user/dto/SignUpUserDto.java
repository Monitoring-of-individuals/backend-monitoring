package ru.monitoring.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpUserDto {
    @Schema(description = "Имя пользователя", example = "Jon")
    @NotBlank
    @Size(min = 2, max = 25, message = "Длина имени должна быть от 2 до 25 символов")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Conor")
    @NotBlank
    @Size(min = 2, max = 25, message = "Длина фамилии должна быть от 2 до 25 символов")
    private String lastName;
    @NotBlank
    @Email
    @Schema(description = "Адрес электронной почты", example = "joncon@gmail.com")
    private String email;
    @Pattern(
            regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$")
    @NotBlank
    @Size(min = 6, max = 50, message = "Длина пароля от 6 до 50 символов")
    @Schema(description = "Пароль", example = "my1Pass^")
    private String password;
}
