package ru.monitoring.dto.gibdd;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
// Если вывод результата из кэша
public class Cache {

    private String actual; // Дата актуальности
    private TimeStamp start; // Промежуток начала поиска
    private TimeStamp stop; // Промежуток конца поиска
}
