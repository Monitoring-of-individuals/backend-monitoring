package ru.monitoring.service;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.nalog.InnDto;
import ru.monitoring.dto.nalog.InnResponse;

@UtilityClass
public class InnMapper {

    public InnDto toResponseDto(InnResponse response) {
        return InnDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .found(response.getFound())
                .inn(response.getInn())
                .build();
    }
}
