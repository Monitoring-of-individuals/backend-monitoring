package ru.monitoring.model;

import lombok.*;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;

// Предварительный вид. Требует доработки.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Report {

    private FsspResponse fsspResponse;
    private InnResponse innResponse;
    private SelfEmplResponse selfEmplResponse;
    private PassportCheckResponse passportCheckResponse;
    private GibddResponse gibddResponse;
    private RosFinMonResponse rosFinMonResponse;
    private BankruptResponse bankruptResponse;
}
