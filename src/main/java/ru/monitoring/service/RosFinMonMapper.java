package ru.monitoring.service;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.rosfinmon.RosFinMonDto;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;

@UtilityClass
public class RosFinMonMapper {

    public RosFinMonDto toResponseDto(RosFinMonResponse response) {
        return RosFinMonDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .found(response.getFound())
                .count(response.getCount())
                .result(response.getResult())
                .build();
    }

}
