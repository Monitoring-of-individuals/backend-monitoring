package ru.monitoring.service;

import ru.monitoring.dto.ResponseDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.model.Report;

/**
 * Поскольку не все данные, поставляемые поставщиком по каждому запросу необходимо использовать в финальном отчете, то в
 * будущем необходимо добавить логику обработки отчета, с целью формирования выходного объекта в правильном виде.
 */
public final class ReportBuilder {
    private FsspResponse fsspResponse;
    private InnResponse innResponse;
    private SelfEmplResponse selfEmplResponse;
    private PassportCheckResponse passportCheckResponse;
    private GibddResponse gibddResponse;
    private RosFinMonResponse rosFinMonResponse;
    private BankruptResponse bankruptResponse;

    public ReportBuilder() {
    }

    public ReportBuilder addFsspResponse(FsspResponse fsspResponse) {
        this.fsspResponse = fsspResponse;
        return this;
    }

    public ReportBuilder addInnResponse(InnResponse innResponse) {
        this.innResponse = innResponse;
        return this;
    }

    public ReportBuilder addSelfEmplResponse(SelfEmplResponse selfEmplResponse) {
        this.selfEmplResponse = selfEmplResponse;
        return this;
    }

    public ReportBuilder addPassportCheckResponse(PassportCheckResponse passportCheckResponse) {
        this.passportCheckResponse = passportCheckResponse;
        return this;
    }

    public ReportBuilder addGibddResponse(GibddResponse gibddResponse) {
        this.gibddResponse = gibddResponse;
        return this;
    }

    public ReportBuilder addRosFinMonResponse(RosFinMonResponse rosFinMonResponse) {
        this.rosFinMonResponse = rosFinMonResponse;
        return this;
    }

    public ReportBuilder addBankruptResponse(BankruptResponse bankruptResponse) {
        this.bankruptResponse = bankruptResponse;
        return this;
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    public Report build() {
        Report report = new Report();
        report.setFsspResponse(fsspResponse);
        report.setInnResponse(innResponse);
        report.setSelfEmplResponse(selfEmplResponse);
        report.setPassportCheckResponse(passportCheckResponse);
        report.setGibddResponse(gibddResponse);
        report.setRosFinMonResponse(rosFinMonResponse);
        report.setBankruptResponse(bankruptResponse);
        return report;
    }

    // Проверяем, что сервис вернул ответ с ошибкой, но со статусом 200 или вернул пустой ошибку со статусом 4хх
    // (надо добавить в клиента возвращение ответа со значением переменных null).
    private ResponseDto checkingIfStatusNot200(ResponseDto dto) {
        if (dto.getStatus() == null) {
            dto.setStatus(500); // какой статус возвращать?
            dto.setErrorMsg("Сервис вернул ошибку. Запрос не был выполнен.");
            return dto;
        }
        if ( dto.getError() != null) {
            ResponseDto newDto = new ResponseDto();
            newDto.setStatus(500);
            newDto.setErrorMsg("Сервис вернул ошибку: " + dto.getMessage() + " Запрос не был выполнен.");
            return newDto;
        }

        return dto;
    }
}