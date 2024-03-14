package ru.monitoring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PersonIfoDto {
    private String lastName;
    private String firstName;
    private String secondName;
    private String birthDate;
    private String passportSeries;
    private String passportNumber;
    private String driverIdSeriesNumber;
    private String driverIdDate;
}
