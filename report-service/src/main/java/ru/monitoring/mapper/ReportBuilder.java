package ru.monitoring.mapper;

import ru.monitoring.dto.ResponseDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspDto;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddDto;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckDto;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnDto;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplDto;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.RosFinMonDto;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.model.Report;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Поскольку не все данные, поставляемые поставщиком по каждому запросу необходимо использовать в финальном отчете,
 * в данном классе происходит сборка финального отчета, включая маппинг объектов и замену ошибок на null.
 */
public final class ReportBuilder {
    private FsspDto fsspResponse;
    private InnDto innResponse;
    private SelfEmplDto selfEmplResponse;
    private PassportCheckDto passportCheckResponse;
    private GibddDto gibddResponse;
    private RosFinMonDto rosFinMonResponse;
    private BankruptDto bankruptResponse;
    private Boolean isSuccess = false;
    private BigDecimal reportPrice = new BigDecimal("0");

    public ReportBuilder() {
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    public ReportBuilder addFsspResponse(FsspResponse fsspResponse) {
        this.fsspResponse = checkingIfStatusNot200(fsspResponse) ? FsspMapper.toResponseDto(
                fsspResponse) : null;
        if (this.fsspResponse != null) {
            this.isSuccess = true;
            if (fsspResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(fsspResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addInnResponse(InnResponse innResponse) {
        this.innResponse = checkingIfStatusNot200(innResponse) ? InnMapper.toResponseDto(
                innResponse) : null;
        if (this.innResponse != null) {
            this.isSuccess = true;
            if (innResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(innResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addSelfEmplResponse(SelfEmplResponse selfEmplResponse) {
        this.selfEmplResponse = checkingIfStatusNot200(selfEmplResponse)
                ? SelfEmplMapper.toResponseDto(selfEmplResponse) : null;
        if (this.selfEmplResponse != null) {
            this.isSuccess = true;
            if (selfEmplResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(selfEmplResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addPassportCheckResponse(PassportCheckResponse passportCheckResponse) {
        this.passportCheckResponse = checkingIfStatusNot200(passportCheckResponse)
                ? PassportChecMapper.toResponseDto(passportCheckResponse) : null;
        if (this.passportCheckResponse != null) {
            this.isSuccess = true;
            if (passportCheckResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(passportCheckResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addGibddResponse(GibddResponse gibddResponse) {
        this.gibddResponse = checkingIfStatusNot200(gibddResponse) ? GibddMapper.toResponseDto(
                gibddResponse) : null;
        if (this.gibddResponse != null) {
            this.isSuccess = true;
            if (gibddResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(gibddResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addRosFinMonResponse(RosFinMonResponse rosFinMonResponse) {
        this.rosFinMonResponse = checkingIfStatusNot200(rosFinMonResponse)
                ? RosFinMonMapper.toResponseDto(rosFinMonResponse) : null;
        if (this.rosFinMonResponse != null) {
            this.isSuccess = true;
            if (rosFinMonResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(rosFinMonResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public ReportBuilder addBankruptResponse(BankruptResponse bankruptResponse) {
        this.bankruptResponse = checkingIfStatusNot200(bankruptResponse)
                ? BankruptMapper.toResponseDto(bankruptResponse) : null;
        if (this.bankruptResponse != null) {
            this.isSuccess = true;
            if (bankruptResponse.getInquiry() != null) {
                this.reportPrice = this.reportPrice.add(bankruptResponse.getInquiry().getPrice());
            }
        }
        return this;
    }

    public Report build() {
        Report report = new Report();
        report.setIsSuccess(isSuccess);
        report.setReportDateTime(LocalDateTime.now());
        report.setFsspResponse(fsspResponse);
        report.setInnResponse(innResponse);
        report.setSelfEmplResponse(selfEmplResponse);
        report.setPassportCheckResponse(passportCheckResponse);
        report.setGibddResponse(gibddResponse);
        report.setRosFinMonResponse(rosFinMonResponse);
        report.setBankruptResponse(bankruptResponse);
        report.setReportPrice(reportPrice);
        return report;
    }

    /**
     * ТОЛЬКО ДЛЯ ApiCloudClient<p>
     * Проверяем:<p>
     * 1 - что сервис вернул ответ с ошибкой, тогда в ответе ошибка.<p>
     * 2 - что поле статуса не null и не 200, а поле сообщение заполнено - тогда в ответе ошибка.<p>
     * Во всех остальных случаях получен отчет от базы ведомства, а в случае отсутствия ИНН проверки на самозанятость
     * и банкротство не проводились.
     */

    private boolean checkingIfStatusNot200(ResponseDto dto) {
        if (dto.getError() != null) {
            return false;
        }
        if (dto.getStatus() != null && dto.getStatus() != 200 && dto.getMessage() != null) {
            return false;
        }
        return true;
    }
}