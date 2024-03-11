package ru.monitoring.dto.nalog;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelfEmplResponse extends ResponseDto {
//    private int status;
    private Boolean found;
    private String inn;
    private String date;
    private Integer NPD; // Статус НПД (1 - является налогоплательщиком на профессиональный доход, 0 - не является)
/*    private String message;
    private Inquiry inquiry;
    // Общие данные для всх ответов
    private String error;
    private String errormsg;*/
}