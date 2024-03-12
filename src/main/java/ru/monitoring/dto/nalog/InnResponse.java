package ru.monitoring.dto.nalog;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.Inquiry;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InnResponse {
    private int status;
    private boolean found;
    private String inn;
    private String message;
    private Inquiry inquiry;
    // В случае если TIME_MAX_CONNECT - Достигнуто максимальное количество коннектов, при которых ресурс не вернул результата
    private String error;
    @JSONField(name = "errormsg")
    private String errorMsg;
}
