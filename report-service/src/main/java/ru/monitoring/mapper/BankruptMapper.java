package ru.monitoring.mapper;

import lombok.experimental.UtilityClass;
import ru.monitoring.dto.fedres_banckrupt.BankruptDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;

@UtilityClass
public class BankruptMapper {
    public BankruptDto toResponseDto(BankruptResponse response) {
        return BankruptDto.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .totalCount(response.getTotalCount())
                .rez(response.getRez())
                .num(response.getNum())
                .build();
    }
}
