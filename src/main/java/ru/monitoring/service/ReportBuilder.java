package ru.monitoring.service;

import ru.monitoring.dto.ResponseDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
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
    private ResponseDto fsspResponse;
    private ResponseDto innResponse;
    private ResponseDto selfEmplResponse;
    private ResponseDto passportCheckResponse;
    private ResponseDto gibddResponse;
    private ResponseDto rosFinMonResponse;
    private ResponseDto bankruptResponse;

    public ReportBuilder() {
    }

    public ReportBuilder addFsspResponse(ResponseDto fsspResponse) {
        this.fsspResponse = checkingIfStatusNot200(fsspResponse);
        return this;
    }

    public ReportBuilder addInnResponse(InnResponse innResponse) {
        this.innResponse = checkingIfStatusNot200(innResponse);
        return this;
    }

    public ReportBuilder addSelfEmplResponse(SelfEmplResponse selfEmplResponse) {
        this.selfEmplResponse = checkingIfStatusNot200(selfEmplResponse);
        return this;
    }

    public ReportBuilder addPassportCheckResponse(PassportCheckResponse passportCheckResponse) {
        this.passportCheckResponse = checkingIfStatusNot200(passportCheckResponse);
        return this;
    }

    public ReportBuilder addGibddResponse(GibddResponse gibddResponse) {
        this.gibddResponse = checkingIfStatusNot200(gibddResponse);
        return this;
    }

    public ReportBuilder addRosFinMonResponse(RosFinMonResponse rosFinMonResponse) {
        this.rosFinMonResponse = checkingIfStatusNot200(rosFinMonResponse);
        return this;
    }

    public ReportBuilder addBankruptResponse(BankruptResponse bankruptResponse) {
        this.bankruptResponse = checkingIfStatusNot200(bankruptResponse);
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
        if ( dto.getError() != null) {
            return new ResponseDto();
        }

        return dto;
    }
}