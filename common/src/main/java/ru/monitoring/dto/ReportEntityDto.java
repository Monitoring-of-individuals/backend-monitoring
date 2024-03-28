package ru.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportEntityDto {

    private String personLastName;
    private String personFirstName;
    private String personFatherName;
    private LocalDate personDateOfBirth;
    private String personPassport;
    private String personDrivingLicence;
    private LocalDate personDateOfLicence;
    private String report;
    private Boolean isSuccess;
    private LocalDateTime reportDateTime;
    private BigDecimal reportPrice;
}
