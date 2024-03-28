package ru.monitoring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.model.PendingVerificationUser;
import ru.monitoring.model.Role;
import ru.monitoring.model.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User convertSignUpUserDtoToUser(SignUpUserRequestDto signUpUserRequestDto) {
        if (Objects.isNull(signUpUserRequestDto)) {
            return null;
        }
        return User.builder()
                .firstName(signUpUserRequestDto.getFirstName())
                .lastName(signUpUserRequestDto.getLastName())
                .password(passwordEncoder.encode(signUpUserRequestDto.getPassword()))
                .email(signUpUserRequestDto.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }

    public PendingVerificationUser convertSignUpUserDtoToPendingVerificationUser(SignUpUserRequestDto signUpUserRequestDto) {
        return PendingVerificationUser.builder()
                .firstName(signUpUserRequestDto.getFirstName())
                .lastName(signUpUserRequestDto.getLastName())
                .email(signUpUserRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpUserRequestDto.getPassword()))
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
