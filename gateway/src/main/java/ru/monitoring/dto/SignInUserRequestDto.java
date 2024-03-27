package ru.monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.monitoring.utils.validation.Password;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Запрос на аутентификацию")
public class SignInUserRequestDto {

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email
    private String email;
    @NotBlank
    @Password
    private String password;
}
