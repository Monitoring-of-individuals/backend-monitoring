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

}
