package ru.monitoring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.model.ApiCloudBalance;
import ru.monitoring.service.SupplierRequestService;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SupplierRequestService service;

    @GetMapping("/api-cloud/balance")
    public ApiCloudBalance getApiCloudBalance() {
        return service.getApiCloudBalance();
    }
}
