package ru.monitoring.dto.mvd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassportCheckResponse extends ResponseDto {

    @JSONField(name = "rezultat")
    private int result;
    private String info;
}