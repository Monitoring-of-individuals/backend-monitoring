package ru.monitoring.dto.fssp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.Inquiry;

import java.util.List;

// Данные по исполнительным производствам
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FsspResponse {

    private int status; // Статус запрос
    private String countAll; // Всего записей
    private String pagesAll; // Всего страниц найдено
    private int count; // Количество записей в результате
    private int totalLoadedPage; // Загружено страниц в результате (по умолчанию загружается 1 страница, возможно загрузить максимум 4 страницы при переданном параметре searchAll=1)
    private int onlyActual; // Активность фильтра "Только актуальные делопроизводства"
    private List<Record> records;
    // Информация о запросе
    private Inquiry inquiry;
    // В случае если записей в базе нет или приходит ошибка
    private String message;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    @JSONField(name = "errormsg")
    private String errorMsg;

}
