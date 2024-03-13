package ru.monitoring.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.exceptions.ValidateException;
import ru.monitoring.dto.PersonIfoDto;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Slf4j
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

    /*// Тестовый эндпоинт, собирающий все ответы от "поставщика"(эмулятора)
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
    }*/

    @GetMapping("/report")
    public Report getReportByParam(/*@PathVariable(value = "userId") Long userId,*/
                                   @Valid @RequestBody PersonIfoDto dto,
                                   @RequestParam String token) {

        containsEqualLastNameFirstName(dto.getLastName(), dto.getFirstName());

        return service.getReport(dto); // проверить работают ли аннотации
    }
}