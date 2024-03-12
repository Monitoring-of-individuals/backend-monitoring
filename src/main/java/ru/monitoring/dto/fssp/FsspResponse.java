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

    private String countAll; // Всего записей
    private String pagesAll; // Всего страниц найдено
    private Integer count; // Количество записей в результате
    private Integer totalLoadedPage; // Загружено страниц в результате (по умолчанию загружается 1 страница, возможно загрузить максимум 4 страницы при переданном параметре searchAll=1)
    private Integer onlyActual; // Активность фильтра "Только актуальные делопроизводства"
    private List<Record> records;
}