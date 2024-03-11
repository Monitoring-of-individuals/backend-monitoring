package ru.monitoring.dto.nalog;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InnResponse extends ResponseDto {
//    private int status;
    private Boolean found;
    private String inn;
//    private String message;
//    private Inquiry inquiry;
//    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
//    private String error;
//    private String errormsg;
}