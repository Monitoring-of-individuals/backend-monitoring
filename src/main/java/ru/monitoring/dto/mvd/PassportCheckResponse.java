package ru.monitoring.dto.mvd;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassportCheckResponse extends ResponseDto {
//    private int status;
    private Integer rezultat;
    private String info;
//    private Inquiry inquiry;
//    // В случае отрицательного результата проверки или ошибки
//    private int error;
//    private String message;
//    // В случае если 404 TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
//    private String errormsg;
}