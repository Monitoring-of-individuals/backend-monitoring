package ru.monitoring.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.model.Role;
import ru.monitoring.user.model.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User convertSignUpUserDtoToUser(SignUpUserDto signUpUserDto) {
        if (Objects.isNull(signUpUserDto)) {
            return null;
        }
        return User.builder()
                .firstName(signUpUserDto.getFirstName())
                .lastName(signUpUserDto.getLastName())
                .password(passwordEncoder.encode(signUpUserDto.getPassword()))
                .email(signUpUserDto.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }

    public UserResponseDto convertUserToUserResponseDto(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
