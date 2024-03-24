package ru.monitoring.dto.gibdd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Deprivation {

    private String date; // Дата вынесения постановления
    @JSONField(name = "fis_id")
    private String fisId;
    @JSONField(name = "bplace")
    private String birthPlace;
    private String comment; // Комментарий ГИБДД
    @JSONField(name = "reg_name")
    private String regName; // Место рождения нарушителя
    private String state; // Состояние исполнения постановления
    private int srok; // Срок лишения (в месяцах)
    @JSONField(name = "reg_code")
    private String regCode;
    @JSONField(name = "stateinfo")
    private String stateInfo; // Состояние исполнения постановления (расшифровка)
}
