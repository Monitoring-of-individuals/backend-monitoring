package ru.monitoring.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpUserDto {
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Jon")
    private String firstName;
    @NotBlank
    @Schema(description = "Фамилия пользователя", example = "Conor")
    private String lastName;
    @NotBlank
    @Email
    @Schema(description = "Адрес электронной почты", example = "joncon@gmail.com")
    private String email;
    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$")
    @NotBlank
    @Schema(description = "Пароль", example = "my1Pass^")
    private String password;
}
