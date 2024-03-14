package ru.monitoring.dto.nalog;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelfEmplResponse extends ResponseDto {

    private Boolean found;
    private String inn;
    private String date;
    @JSONField(name = "NPD")
    private Integer npd; // Статус НПД (1 - является налогоплательщиком на профессиональный доход, 0 - не является)

    @Override
    public String toString() {
        return "SelfEmplResponse{" +
                "found=" + found +
                ", inn='" + inn + '\'' +
                ", date='" + date + '\'' +
                ", npd=" + npd +
                ", status=" + status +
                ", inquiry=" + inquiry +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}