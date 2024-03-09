package ru.monitoring.dto.gibdd;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Doc {

    private String division; // Кем выдано
    private String date; // Дата выдачи
    private String stag; // Стаж с
    private String bdate; // Дата рождения
    private String num; // Серия и номер
    private String cat; // Категории ТС
    private String type;
    private String srok; // Срок действия
    private String divid;
}
