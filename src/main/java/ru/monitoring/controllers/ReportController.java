package ru.monitoring.controllers;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.exceptions.ValidateException;
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

    // проверка на запрещенные символы
    public void containsOnlyCyrillicAndDash(String input) {
        if (!input.matches("[а-яА-Я\\-]*")) {
            throw new ValidateException("В переменной " + input + " присутствуют запрещенные символы. " +
                    "Разрешено: кириллица и знак \"-\"");
        }
    }

    // проверка на запрещенные символы серия и номер
    public void containsOnlyDigits(String input) {
        if (!input.matches("[0-9]*")) {
            throw new ValidateException("В переменной " + input + " присутствуют запрещенные символы. " +
                    "Разрешено: цифры без пробелов");
        }
    }

    // проверка на совпадение имени и фамилии
    public void containsEqualLastNameFirstName(String lastname, String firstname) {
        String trimmedFirstname = firstname.trim();
        String trimmedLastname = lastname.trim();
        if (trimmedFirstname.equalsIgnoreCase(trimmedLastname)) {
            throw new ValidateException("Фамилия " + trimmedLastname + " и Имя " + trimmedFirstname + " должны различаться");
        }
    }

    // Тестовый эндпоинт, собирающий все ответы от "поставщика"(эмулятора)
    @GetMapping
    public Report getReport() {
        String lastname = "Иванов";
        String firstname = "Иван";
        String secondname = "Иванович";
        String birthdate = "31.03.1995";
        String passportSeria = "1234";
        String passportNomer = "567890";
        String driverIdSeriaNomer = "1234567890";
        String driverIdDate = "07.11.2014";

        return service.getReport(lastname, firstname, secondname, birthdate, passportSeria, passportNomer,
                driverIdSeriaNomer, driverIdDate);
    }

    @GetMapping("/report")
    public Report getReportByParam(/*@PathVariable(value = "userId") Long userId,*/
                                   @RequestParam @Size(min = 2) String lastName,
                                   @RequestParam @Size(min = 2) String firstName,
                                   @RequestParam @Size(min = 2) String secondName,
                                   @RequestParam @Past @DateTimeFormat(pattern = PATTERN_DATE) LocalDate birthDate,
                                   // по этим 2 параметрам серия и номер паспорта уточнить и объединить
                                   @RequestParam @Size (min = 10,
                                           message = "В параметре Серия и номер паспорта должно быть не менее 10 цифр")
                                       String passportSeriya,
                                   @RequestParam @Size (min = 10,
                                           message = "В параметре Серия и номер паспорта должно быть не менее 10 цифр")
                                       String passportNomer,
                                   @RequestParam @Size (min = 10, message = "В параметре Водительское удостоверение" +
                                           " должно быть не менее 10 цифр")
                                       String driverIdSeriyaNomer,
                                   @RequestParam @Past @DateTimeFormat(pattern = PATTERN_DATE) LocalDate driverIdDate,
                                   @RequestParam String token) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
        String birthDay = birthDate.format(formatter); // to String
        String driverDate = driverIdDate.toString();

        containsOnlyCyrillicAndDash(lastName);
        containsOnlyCyrillicAndDash(firstName);
        containsEqualLastNameFirstName(lastName, firstName);
        containsOnlyDigits(passportNomer);
        containsOnlyDigits(driverIdSeriyaNomer);

        return service.getReport(lastName,firstName, secondName, birthDay, passportSeriya, passportNomer, driverIdSeriyaNomer,
                driverDate); // проверь параметры запроса работают ли аннотации
    }

}