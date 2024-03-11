package ru.monitoring.dto.fssp;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

// Данные по исполнительным производствам
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FsspResponse extends ResponseDto {

/*    private int status; // Статус запрос*/
    private String countAll; // Всего записей
    private String pagesAll; // Всего страниц найдено
    private Integer count; // Количество записей в результате
    private Integer totalLoadedPage; // Загружено страниц в результате (по умолчанию загружается 1 страница, возможно загрузить максимум 4 страницы при переданном параметре searchAll=1)
    private Integer onlyActual; // Активность фильтра "Только актуальные делопроизводства"
    private List<Record> records;
/*    // В случае если записей в базе нет или приходит ошибка
    private String message;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    private String errormsg;*/
}