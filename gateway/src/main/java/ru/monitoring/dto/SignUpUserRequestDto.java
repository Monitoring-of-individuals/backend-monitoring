package ru.monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpUserRequestDto {
    @Schema(description = "Имя пользователя", example = "Jon")
    @NotBlank
    @Size(min = 2, max = 25, message = "Длина имени должна быть от 2 до 25 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\s]+$",
            message = "Имя может содержать только буквы русского и английского алфавитов и пробелы")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Conor")
    @NotBlank
    @Size(min = 2, max = 25, message = "Длина фамилии должна быть от 2 до 25 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\s]+$",
            message = "Имя может содержать только буквы русского и английского алфавитов и пробелы")
    private String lastName;
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
