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

    public static PersonIfoDto makeSamplePersonInfoDto() {
        return PersonIfoDto.builder()
                .lastName("Иванов")
                .firstName("Иван")
                .secondName("Иванович")
                .birthDate("31.03.1995")
                .passportSeries("1234")
                .passportNumber("567890")
                .driverIdSeriesNumber("1234567890")
                .driverIdDate("07.11.2014")
                .build();
    }
}
