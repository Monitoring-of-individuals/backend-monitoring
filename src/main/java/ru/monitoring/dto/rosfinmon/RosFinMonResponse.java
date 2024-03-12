package ru.monitoring.dto.rosfinmon;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.Inquiry;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RosFinMonResponse {
    private int status; // Статус ответа
    private boolean found; // Найдены результаты
    private int count; // Количество записей
    private List<Result> result;
    private Inquiry inquiry;
    // В случае если записей в базе нет или приходит ошибка
    private String message;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    @JSONField(name = "errormsg")
    private String errorMsg;

}
