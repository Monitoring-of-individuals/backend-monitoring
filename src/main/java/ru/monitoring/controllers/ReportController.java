package ru.monitoring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final SupplierRequestService service;

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

        return service.getReport(lastname, firstname, secondname, birthdate, passportSeria, passportNomer, driverIdSeriaNomer, driverIdDate);
    }
}
