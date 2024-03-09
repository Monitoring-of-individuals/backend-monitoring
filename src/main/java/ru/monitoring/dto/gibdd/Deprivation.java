package ru.monitoring.dto.gibdd;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Deprivation {

    private String date; // Дата вынесения постановления
    private String fis_id;
    private String bplace;
    private String comment; // Комментарий ГИБДД
    private String reg_name; // Место рождения нарушителя
    private String state; // Состояние исполнения постановления
    private int srok; // Срок лишения (в месяцах)
    private String reg_code;
    private String stateinfo; // Состояние исполнения постановления (расшифровка)
}
