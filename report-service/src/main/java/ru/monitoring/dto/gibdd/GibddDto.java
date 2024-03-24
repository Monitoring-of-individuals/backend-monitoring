package ru.monitoring.dto.gibdd;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GibddDto {
    private Integer status;
    private String message;

    private Doc doc;
    private List<Deprivation> decis; // Ифнормаци о лишениях
    private Boolean found; // Найдено удостоверение или нет, true - найдено | false - не найдено
    private Cache cache; // Если вывод результата из кэша
}
