package ru.monitoring.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private Integer status; // Статус запрос
    private Inquiry inquiry; // Информация о запросе
    // В случае отрицательного результата проверки или ошибки
    private Integer error;
    private String message;
    // В случае если 404 TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    @JSONField(name = "errormsg")
    private String errorMsg;
}