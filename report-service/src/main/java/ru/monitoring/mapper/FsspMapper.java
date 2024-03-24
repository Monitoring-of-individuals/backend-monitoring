package ru.monitoring.mapper;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.fssp.FsspDto;
import ru.monitoring.dto.fssp.FsspResponse;

@UtilityClass
public class FsspMapper {

    public FsspDto toResponseDto(FsspResponse response) {
        return FsspDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .countAll(response.getCountAll())
                .pagesAll(response.getPagesAll())
                .count(response.getCount())
                .totalLoadedPage(response.getTotalLoadedPage())
                .onlyActual(response.getOnlyActual())
                .records(response.getRecords())
                .build();
    }
}
