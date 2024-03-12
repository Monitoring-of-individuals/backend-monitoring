package ru.monitoring.dto.nalog;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.Inquiry;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelfEmplResponse {
    private int status;
    private boolean found;
    private String inn;
    private String date;
    @JSONField(name = "NPD")
    private int npd; // Статус НПД (1 - является налогоплательщиком на профессиональный доход, 0 - не является)
    private String message;
    private Inquiry inquiry;
    // Общие данные для всх ответов
    private String error;
    @JSONField(name = "errormsg")
    private String errorMsg;
}
