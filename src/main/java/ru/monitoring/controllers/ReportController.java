package ru.monitoring.controllers;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import ru.monitoring.exceptions.ValidateException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.dto.PersonIfoDto;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.monitoring.utils.Constants.PATTERN_DATE;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Validated
public class ReportController {

    private final SupplierRequestService service;

    // проверка на совпадение имени и фамилии
    public void containsEqualLastNameFirstName(String lastname, String firstname) {
        String trimmedFirstname = firstname.trim();
        String trimmedLastname = lastname.trim();
        if (trimmedFirstname.equalsIgnoreCase(trimmedLastname)) {
            throw new ValidateException("Фамилия " + trimmedLastname + " и Имя " + trimmedFirstname + " должны различаться");
        }
    }

    // Тестовый эндпоинт, собирающий все ответы от "поставщика"(эмулятора)
    // Пока параметры запроса необязательны, чтобы сработал метод PersonIfoDto.makeSamplePersonInfoDto()
    @GetMapping
    public Report getReport(@RequestParam(required = false) String lastName,
                            @RequestParam(required = false) String firstName,
                            @RequestParam(required = false) String secondName,
                            @RequestParam(required = false) String birthDate,
                            @RequestParam(required = false) String passportSeries,
                            @RequestParam(required = false) String passportNumber,
                            @RequestParam(required = false) String driverIdSeriesNumber,
                            @RequestParam(required = false) String driverIdDate) {
        if (lastName == null || firstName == null || birthDate == null) {
            return service.getReport(PersonIfoDto.makeSamplePersonInfoDto());
        }
        return service.getReport(new PersonIfoDto(lastName, firstName, secondName, birthDate, passportSeries,
                passportNumber, driverIdSeriesNumber, driverIdDate));
    }

    @GetMapping("/report")
    public Report getReportByParam(/*@PathVariable(value = "userId") Long userId,*/
                                   @RequestParam @Size(min = 1, message = "Не введены данные в параметр \"Фамилия\".")
                                   @Pattern(regexp = "[а-яА-Я\\-]*", message = "В переменной \"Фамилия\" присутствуют " +
                                           "запрещенные символы. Разрешено: кириллица и знак \"-\"")
                                   String lastName,
                                   @RequestParam @Pattern(regexp = "[а-яА-Я\\-]*", message = "В переменной \"Имя\" " +
                                           "присутствуют запрещенные символы. Разрешено: кириллица и знак \"-\"")
                                   @Size(min = 1, message = "Не введены данные в параметр \"Имя\".")
                                   String firstName,
                                   @RequestParam String secondName,
                                   @RequestParam @Past @DateTimeFormat(pattern = PATTERN_DATE) LocalDate birthDate,
                                   // по этим 2 параметрам серия и номер паспорта уточнить и объединить
                                   @RequestParam @Size (min = 4, max = 4,
                                           message = "В параметре Серия паспорта не должно быть больше или меньше 4 цифр")
                                   @Pattern(regexp = "[0-9]*", message = "В параметре Серия паспорта присутствуют запрещенные символы." +
                                           " Разрешено: цифры без пробелов")
                                       String passportSeriya,
                                   @RequestParam @Size (min = 6, max = 6,
                                           message = "В параметре номер паспорта не должно быть больше или меньше 6 цифр")
                                   @Pattern(regexp = "[0-9]*", message = "В параметре номер паспорта присутствуют запрещенные символы." +
                                           " Разрешено: цифры без пробелов")
                                       String passportNomer,
                                   @RequestParam @Size (min = 10, max = 10, message = "В параметре Водительское удостоверение" +
                                           " не должно быть больше или меньше 10 цифр. Номер и серия пишутся в одну " +
                                           "строчку без пробела.")
                                   @Pattern(regexp = "[0-9]*", message = "В параметре Водительское удостоверение " +
                                           "присутствуют запрещенные символы. Разрешено: цифры без пробелов")
                                       String driverIdSeriyaNomer,
                                   @RequestParam @Past @DateTimeFormat(pattern = PATTERN_DATE) LocalDate driverIdDate,
                                   @RequestParam String token) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
        String birthDay = birthDate.format(formatter); // to String
        String driverDate = driverIdDate.toString();

        containsEqualLastNameFirstName(lastName, firstName);

        return service.getReport(new PersonIfoDto(lastName,firstName, secondName, birthDay, passportSeriya, passportNomer,
                driverIdSeriyaNomer, driverDate)); // проверь параметры запроса работают ли аннотации
    }
}