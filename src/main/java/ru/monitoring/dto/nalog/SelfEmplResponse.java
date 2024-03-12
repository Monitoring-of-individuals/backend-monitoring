package ru.monitoring.dto.nalog;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelfEmplResponse extends ResponseDto {

    private Boolean found;
    private String inn;
    private String date;
    @JSONField(name = "NPD")
    private int npd; // Статус НПД (1 - является налогоплательщиком на профессиональный доход, 0 - не является)
}