package ru.monitoring.user;

import ru.monitoring.dto.SignInUserRequestDto;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.model.User;

public class CreatorTestEntities {

    public static SignUpUserRequestDto getSignUpUserDto() {
        return new SignUpUserRequestDto("FirstName", "LastName", "lastName@email.com", "pas1W^");
    }

    public static SignInUserRequestDto getSignInUserDto() {
        return new SignInUserRequestDto("lastName@email.com", "pas1W^");
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .email("lastName@email.com")
                .password("pas1W^")
                .build();
    }

    public static User getUserWithoutId() {
        return User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .email("lastName@email.com")
                .password("pas1W^")
                .build();
    }

    public static UserResponseDto getUserResponseDto() {
        return UserResponseDto.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .email("lastName@email.com")
                .build();
    }

    public static String getToken() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...";
    }
}
