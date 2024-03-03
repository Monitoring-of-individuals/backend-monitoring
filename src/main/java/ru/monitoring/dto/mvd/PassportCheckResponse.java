package ru.monitoring.dto.mvd;

import lombok.*;
import ru.monitoring.dto.Inquiry;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassportCheckResponse {
    private int status;
    private int rezultat;
    private String info;
    private Inquiry inquiry;
    // В случае отрицательного результата проверки
    private int error;
    private String message;
    // В случае если 404 TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String errormsg;
}
