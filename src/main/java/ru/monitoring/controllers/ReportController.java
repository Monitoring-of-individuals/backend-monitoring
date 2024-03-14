package ru.monitoring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.dto.PersonIfoDto;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final SupplierRequestService service;

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
        return service.getReport(new PersonIfoDto(lastName, firstName, secondName, birthDate, passportSeries,
                passportNumber, driverIdSeriesNumber, driverIdDate));
    }
}
