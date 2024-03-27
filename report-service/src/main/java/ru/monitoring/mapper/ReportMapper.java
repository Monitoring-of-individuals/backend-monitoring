package ru.monitoring.mapper;

import com.alibaba.fastjson2.JSON;
import lombok.experimental.UtilityClass;
import ru.monitoring.dto.PersonInfoRequestDto;
import ru.monitoring.dto.ReportDto;
import ru.monitoring.dto.UserHistoryReportDto;
import ru.monitoring.model.Report;
import ru.monitoring.model.ReportEntity;
import ru.monitoring.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.monitoring.utils.Constants.PATTERN_DATE;

@UtilityClass
public class ReportMapper {

    public ReportDto toReportDto(Report report) {
        return ReportDto.builder()
                .fsspResponse(report.getFsspResponse())
                .innResponse(report.getInnResponse())
                .selfEmplResponse(report.getSelfEmplResponse())
                .passportCheckResponse(report.getPassportCheckResponse())
                .gibddResponse(report.getGibddResponse())
                .rosFinMonResponse(report.getRosFinMonResponse())
                .bankruptResponse(report.getBankruptResponse())
                .isSuccess(report.getIsSuccess())
                .reportDateTime(report.getReportDateTime())
                .build();
    }

    public UserHistoryReportDto toUserHistoryReportDto(ReportEntity reportEntity) {
        return UserHistoryReportDto.builder()
                .isSuccess(reportEntity.getIsSuccess())
                .reportDateTime(reportEntity.getReportDateTime())
                .personFirstName(reportEntity.getPersonFirstName())
                .personLastName(reportEntity.getPersonLastName())
                .personFatherName(reportEntity.getPersonFatherName())
                .reportJsonString(reportEntity.getReport())
                .build();
    }

    public ReportEntity toReportEntity(User user, Report report, PersonInfoRequestDto personInfo) {
        String reportString = JSON.toJSONString(report);

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setUser(user);
        reportEntity.setPersonFirstName(personInfo.getFirstName());
        reportEntity.setPersonLastName(personInfo.getLastName());
        reportEntity.setPersonFatherName(personInfo.getFatherName());
        reportEntity.setPersonDateOfBirth(LocalDate.parse(personInfo.getDateOfBirth(), DateTimeFormatter.ofPattern(PATTERN_DATE)));
        reportEntity.setPersonPassport(personInfo.getPassport());
        reportEntity.setPersonDrivingLicence(personInfo.getDrivingLicence());
        reportEntity.setPersonDateOfLicence(LocalDate.parse(personInfo.getDateOfLicence(),DateTimeFormatter.ofPattern(PATTERN_DATE)));
        reportEntity.setReport(reportString);
        reportEntity.setIsSuccess(report.getIsSuccess());
        reportEntity.setReportDateTime(report.getReportDateTime());
        reportEntity.setReportPrice(report.getReportPrice());

        return reportEntity;
    }
}
