package ru.monitoring.dto.rosfinmon;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {

    private String id; // id в базе
    private String type; // тип (fiz - физическое лицо, ur - юридическое лицо)
    private String name; // ФИО
    private String birth; // Дата рождения
    private String place; // Место рождения
}
