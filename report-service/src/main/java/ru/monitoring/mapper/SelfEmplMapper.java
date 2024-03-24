package ru.monitoring.mapper;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.nalog.SelfEmplDto;
import ru.monitoring.dto.nalog.SelfEmplResponse;

@UtilityClass
public class SelfEmplMapper {

    public SelfEmplDto toResponseDto(SelfEmplResponse response) {
        return SelfEmplDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .found(response.getFound())
                .inn(response.getInn())
                .date(response.getDate())
                .npd(response.getNpd())
                .build();
    }
}
