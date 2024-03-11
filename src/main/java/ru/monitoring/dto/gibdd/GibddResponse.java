package ru.monitoring.dto.gibdd;

import lombok.*;
import ru.monitoring.dto.Inquiry;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GibddResponse extends ResponseDto {
    private Doc doc;
    private List<Deprivation> decis; // Ифнормаци о лишениях
/*    private int status; // Статус ответа*/
    private Boolean found; // Найдено удостоверение или нет, true - найдено | false - не найдено
//    private Inquiry inquiry; // Информация о запросе
    private Cache cache; // Если вывод результата из кэша
    // В случае отрицательного ответа от базу
/*    private String message; // Требует уточнения
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    private String errormsg;*/
}