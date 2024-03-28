package ru.monitoring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.monitoring.utils.validation.NameSurname;
import ru.monitoring.utils.validation.Password;

@Data
@AllArgsConstructor
public class SignUpUserRequestDto {
    @NotBlank
    @NameSurname
    private String firstName;
    @NotBlank
    @NameSurname
    private String lastName;
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Size(min = 5, max = 50, message = "Длина электронной почты должна быть от 5 до 50 символов")
    @Email
    private String email;
    @NotBlank
    @Password
    private String password;
}
