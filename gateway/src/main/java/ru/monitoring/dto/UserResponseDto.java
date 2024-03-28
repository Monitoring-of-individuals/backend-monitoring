package ru.monitoring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
