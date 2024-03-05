package ru.monitoring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final SupplierRequestService service;

    @GetMapping
    public Report getReport(/*@RequestParam String lastname,
                            @RequestParam String firstname,
                            @RequestParam String secondname,
                            @RequestParam String birthdate,
                            @RequestParam String passportSeria,
                            @RequestParam String passportNomer,
                            @RequestParam String driverIdSeriaNomer,
                            @RequestParam String driverIdDate*/) {
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
