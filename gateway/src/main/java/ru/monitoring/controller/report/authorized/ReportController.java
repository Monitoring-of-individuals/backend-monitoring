package ru.monitoring.controller.report.authorized;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.monitoring.client.IReportServiceClient;
import ru.monitoring.dto.PersonInfoRequestDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {
    private final IReportServiceClient reportServiceClient;

    @PostMapping("/report")
    public ResponseEntity<String> getReport(@Valid @RequestBody
                                            PersonInfoRequestDto personInfoRequestDto) {
        String report = reportServiceClient.getReport(personInfoRequestDto);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
