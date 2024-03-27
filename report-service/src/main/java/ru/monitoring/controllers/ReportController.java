package ru.monitoring.controllers;

import com.alibaba.fastjson2.JSON;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.dto.PersonInfoRequestDto;
import ru.monitoring.dto.ReportDto;
import ru.monitoring.dto.UserHistoryReportDto;
import ru.monitoring.exceptions.ValidateException;
import ru.monitoring.model.Report;
import ru.monitoring.service.SupplierRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ReportController {

    private final SupplierRequestService service;

    @PostMapping("/report")
    public ReportDto getReportByParam(@RequestParam(value = "userId") Long userId,
                                      @Valid @RequestBody PersonInfoRequestDto dto/*,
                                   @RequestParam String token*/) {
        log.info("Get report by parameters: {}", dto);

        containsEqualLastNameFirstName(dto.getLastName(), dto.getFirstName());

        return service.getReport(userId, dto);
    }

    @GetMapping("report/{userId}/history")
    public List<UserHistoryReportDto> getUserReportsHistory(@PathVariable Long userId,
                                                            @RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "10") int pageSize) {
        log.info("Get report history for user: {}", userId);

        return service.getUserReportHistory(userId, pageNumber, pageSize);
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