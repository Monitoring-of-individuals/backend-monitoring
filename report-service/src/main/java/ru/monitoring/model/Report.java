package ru.monitoring.model;

import lombok.*;
import ru.monitoring.dto.fedres_banckrupt.BankruptDto;
import ru.monitoring.dto.fssp.FsspDto;
import ru.monitoring.dto.gibdd.GibddDto;
import ru.monitoring.dto.mvd.PassportCheckDto;
import ru.monitoring.dto.nalog.InnDto;
import ru.monitoring.dto.nalog.SelfEmplDto;
import ru.monitoring.dto.rosfinmon.RosFinMonDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Предварительный вид. Требует доработки.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Report {

    private FsspDto fsspResponse;
    private InnDto innResponse;
    private SelfEmplDto selfEmplResponse;
    private PassportCheckDto passportCheckResponse;
    private GibddDto gibddResponse;
    private RosFinMonDto rosFinMonResponse;
    private BankruptDto bankruptResponse;
    private Boolean isSuccess; // если хотя бы одно из полей выше заполнено не null
    private LocalDateTime reportDateTime;
    private BigDecimal reportPrice;
}