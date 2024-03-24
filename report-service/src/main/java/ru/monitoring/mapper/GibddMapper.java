package ru.monitoring.mapper;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.gibdd.GibddDto;
import ru.monitoring.dto.gibdd.GibddResponse;

@UtilityClass
public class GibddMapper {

    public GibddDto toResponseDto(GibddResponse response) {
        return GibddDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .doc(response.getDoc())
                .decis(response.getDecis())
                .found(response.getFound())
                .cache(response.getCache())
                .build();
    }
}
