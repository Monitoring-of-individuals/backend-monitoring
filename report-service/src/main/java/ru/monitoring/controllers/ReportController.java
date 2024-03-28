package ru.monitoring.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.dto.PersonInfoRequestDto;
import ru.monitoring.dto.ReportEntityDto;
import ru.monitoring.exceptions.ValidateException;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ReportController {

    private final SupplierRequestService service;

    @PostMapping("/report")
    public ReportEntityDto getReportByParam(@Valid @RequestBody PersonInfoRequestDto dto) {
        log.info("Get report by parameters: {}", dto);

        containsEqualLastNameFirstName(dto.getLastName(), dto.getFirstName());

        return service.getReport(dto);
    }


    // проверка на совпадение имени и фамилии
    public void containsEqualLastNameFirstName(String lastname,
                                               String firstname) {
        String trimmedFirstname = firstname.trim();
        String trimmedLastname = lastname.trim();
        if (trimmedFirstname.equalsIgnoreCase(trimmedLastname)) {
            throw new ValidateException("Фамилия " + trimmedLastname + " и Имя " + trimmedFirstname
                    + " должны различаться");
        }
    }
}