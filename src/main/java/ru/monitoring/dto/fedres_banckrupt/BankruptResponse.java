package ru.monitoring.dto.fedres_banckrupt;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankruptResponse extends ResponseDto {
   /* private int status;*/
    private int totalCount; // Количество найденных записей
    private List<Rez> rez;
/*    private Inquiry inquiry;*/
    // В случае если не нашли
    private Integer num; // Количество найденных записей
/*    private String message;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    private String errormsg;*/
}