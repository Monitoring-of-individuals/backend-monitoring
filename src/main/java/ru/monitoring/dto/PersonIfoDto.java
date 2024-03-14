package ru.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

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

    @NotBlank
    @Size(min = 1, message = LAST_NAME_MESSAGES)
    @Pattern(regexp = "^[а-яёА-ЯЁ]+(?:-[а-яёА-ЯЁ]+)*$", message = LAST_NAME_REGEXP_MESSAGE)
    private String lastName;

    @NotBlank
    @Size(min = 1, message = FIRST_NAME_MESSAGES)
    @Pattern(regexp = "^[а-яёА-ЯЁ]+(?:-[а-яёА-ЯЁ]+)*$", message = FIRST_NAME_REGEXP_MESSAGE)
    private String firstName;

    @Pattern(regexp = "^[а-яёА-ЯЁ]+(?:-[а-яёА-ЯЁ]+)*$", message = FIRST_NAME_REGEXP_MESSAGE)
    private String fatherName;

    @NotNull
    @Past
    @JsonFormat(pattern = PATTERN_DATE)
    private LocalDate dateOfBirth;

    @NotBlank
    @Size (min = 10, max = 10, message = PASSPORT_MESSAGE)
    @Pattern(regexp = "[0-9]*", message = PASSPORT_REGEXP_MESSAGE)
    private String passport;

    @Size (min = 10, max = 10, message = DRIVER_MESSAGE)
    @Pattern(regexp = "[0-9]*", message = DRIVER_REGEXP_MESSAGE)
    private String drivingLicence;

    @Past
    @JsonFormat(pattern = PATTERN_DATE)
    private LocalDate dateOfLicence;


    public String getDateOfBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
        return dateOfBirth.format(formatter);
    }

    public String getDateOfLicence() {
        if (dateOfLicence != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
            return dateOfLicence.format(formatter);
        }

        return null;
    }
}