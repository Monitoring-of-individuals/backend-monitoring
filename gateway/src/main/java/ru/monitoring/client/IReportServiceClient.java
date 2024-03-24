package ru.monitoring.client;

import ru.monitoring.dto.PersonInfoRequestDto;

public interface IReportServiceClient {
    String getReport(PersonInfoRequestDto personInfoRequestDto);
}
