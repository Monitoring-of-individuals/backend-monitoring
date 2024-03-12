package ru.monitoring.dto.gibdd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TimeStamp {
    @JSONField(name = "timestamp")
    private long timeStamp;
    private String date;
}
