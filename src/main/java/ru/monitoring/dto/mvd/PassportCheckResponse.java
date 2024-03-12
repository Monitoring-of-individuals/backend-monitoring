package ru.monitoring.dto.mvd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.Inquiry;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassportCheckResponse {
    private int status;
    @JSONField(name = "rezultat")
    private int result;
    private String info;
    private Inquiry inquiry;
    // В случае отрицательного результата проверки или ошибки
    private String error;
    private String message;
    // В случае если 404 TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    @JSONField(name = "errormsg")
    private String errorMsg;
}
