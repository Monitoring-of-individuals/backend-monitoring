package ru.monitoring.dto.nalog;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InnResponse extends ResponseDto {

    private Boolean found;
    private String inn;

    @Override
    public String toString() {
        return "InnResponse{" +
                "found=" + found +
                ", inn='" + inn + '\'' +
                ", status=" + status +
                ", inquiry=" + inquiry +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}