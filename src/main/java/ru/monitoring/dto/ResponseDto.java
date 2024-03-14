package ru.monitoring.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    protected Integer status; // Статус запрос
    protected Inquiry inquiry; // Информация о запросе
    // В случае отрицательного результата проверки или ошибки
    protected String error;
    protected String message;
    // В случае если 404 TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    @JSONField(name = "errormsg")
    protected String errorMsg;
}