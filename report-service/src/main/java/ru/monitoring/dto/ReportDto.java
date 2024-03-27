package ru.monitoring.dto;

import lombok.*;
import ru.monitoring.dto.fedres_banckrupt.BankruptDto;
import ru.monitoring.dto.fssp.FsspDto;
import ru.monitoring.dto.gibdd.GibddDto;
import ru.monitoring.dto.mvd.PassportCheckDto;
import ru.monitoring.dto.nalog.InnDto;
import ru.monitoring.dto.nalog.SelfEmplDto;
import ru.monitoring.dto.rosfinmon.RosFinMonDto;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private FsspDto fsspResponse;
    private InnDto innResponse;
    private SelfEmplDto selfEmplResponse;
    private PassportCheckDto passportCheckResponse;
    private GibddDto gibddResponse;
    private RosFinMonDto rosFinMonResponse;
    private BankruptDto bankruptResponse;
    private Boolean isSuccess; // если хотя бы одно из полей выше заполнено не null
    private LocalDateTime reportDateTime;
}
