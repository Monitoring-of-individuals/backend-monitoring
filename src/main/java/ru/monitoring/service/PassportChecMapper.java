package ru.monitoring.service;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.mvd.PassportCheckDto;
import ru.monitoring.dto.mvd.PassportCheckResponse;

@UtilityClass
public class PassportChecMapper {
    public PassportCheckDto toResponseDto(PassportCheckResponse response) {
        return PassportCheckDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .result(response.getResult())
                .info(response.getInfo())
                .build();
    }
}
