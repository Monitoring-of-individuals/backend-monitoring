package ru.monitoring.model;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

// Предварительный вид. Требует доработки.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Report {

    private ResponseDto fsspResponse;
    private ResponseDto innResponse;
    private ResponseDto selfEmplResponse;
    private ResponseDto passportCheckResponse;
    private ResponseDto gibddResponse;
    private ResponseDto rosFinMonResponse;
    private ResponseDto bankruptResponse;
}