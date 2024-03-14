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

    @GetMapping("/report")
    public Report getReportByParam(/*@PathVariable(value = "userId") Long userId,*/
                                   @Valid @RequestBody PersonIfoDto dto/*,
                                   @RequestParam String token*/) {
        log.info("Get report by parameters: {}", dto);

        containsEqualLastNameFirstName(dto.getLastName(), dto.getFirstName());

        return service.getReport(dto);
    }
}