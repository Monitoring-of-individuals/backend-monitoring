package ru.monitoring.user;

import ru.monitoring.user.dto.SignInUserDto;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.model.User;

public class CreatorTestEntities {

    public static SignUpUserDto getSignUpUserDto() {
        return new SignUpUserDto("FirstName", "LastName", "lastName@email.com", "pas1W^");
    }

    public static SignInUserDto getSignInUserDto() {
        return new SignInUserDto("lastName@email.com", "pas1W^");
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
                .token(getToken())
                .build();
    }

    public static String getToken() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...";
    }
}
