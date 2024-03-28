package ru.monitoring.mapper;

import com.alibaba.fastjson2.JSON;
import lombok.experimental.UtilityClass;
import ru.monitoring.dto.PersonInfoRequestDto;
import ru.monitoring.dto.ReportEntityDto;
import ru.monitoring.model.Report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.monitoring.utils.Constants.PATTERN_DATE;

@UtilityClass
public class ReportMapper {

    public ReportEntityDto toReportEntity(Report report, PersonInfoRequestDto personInfo) {
        String reportString = JSON.toJSONString(report);

        ReportEntityDto reportEntity = new ReportEntityDto();
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
