package ru.monitoring.dto.rosfinmon;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RosFinMonResponse extends ResponseDto {
/*    private int status; // Статус ответа*/
    private Boolean found; // true - результаты найдены, false - данные отсутствуют
    private Integer count; // Количество записей
    private List<Result> result;
/*    private Inquiry inquiry;*/
/*    // В случае если записей в базе нет или приходит ошибка
    private String message;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    private String errormsg;*/
}