package ru.monitoring.dto.mvd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.monitoring.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassportCheckResponse extends ResponseDto {

    @JSONField(name = "rezultat")
    private Integer result;
    private String info;

    @Override
    public String toString() {
        return "PassportCheckResponse{" + "result=" + result + ", info='" + info + '\''
                + ", status=" + status + ", inquiry=" + inquiry + ", error='" + error + '\''
                + ", message='" + message + '\'' + ", errorMsg='" + errorMsg + '\'' + '}';
    }
}