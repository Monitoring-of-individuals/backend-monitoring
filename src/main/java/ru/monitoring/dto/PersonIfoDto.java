package ru.monitoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.monitoring.utils.Constants.PATTERN_DATE;
import static ru.monitoring.utils.Messages.*;
import static ru.monitoring.utils.Messages.DRIVER_REGEXP_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PersonIfoDto {

    @NotBlank(message = LAST_NAME_MESSAGES)
    @Size(min = 1, message = LAST_NAME_MESSAGES)
    @Pattern(regexp = "^[а-яёА-ЯЁ]+(?:-[а-яёА-ЯЁ]+)*$", message = LAST_NAME_REGEXP_MESSAGE)
    private String lastName;

    @NotBlank(message = FIRST_NAME_MESSAGES)
    @Size(min = 1, message = FIRST_NAME_MESSAGES)
    @Pattern(regexp = "^[а-яёА-ЯЁ]+(?:-[а-яёА-ЯЁ]+)*$", message = FIRST_NAME_REGEXP_MESSAGE)
    private String firstName;
    private String fatherName;
    @Past
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dateOfBirth;

    @Size (min = 10, max = 10, message = PASSPORT_MESSAGE)
    @Pattern(regexp = "[0-9]*", message = PASSPORT_REGEXP_MESSAGE)
    private String passport;

    @Size (min = 10, max = 10, message = DRIVER_MESSAGE)
    @Pattern(regexp = "[0-9]*", message = DRIVER_REGEXP_MESSAGE)
    private String drivingLicence;

    @Past @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dateOfLicence;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);

    public String getDateOfBirth() {
        return dateOfBirth.format(formatter);
    }

    public String getDateOfLicence() {
        return dateOfLicence.format(formatter);
    }

    /*    public static PersonIfoDto makeSamplePersonInfoDto() {
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
    }*/
}
