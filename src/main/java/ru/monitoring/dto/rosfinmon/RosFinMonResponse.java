package ru.monitoring.dto.rosfinmon;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RosFinMonResponse extends ResponseDto {

    private Boolean found; // true - результаты найдены, false - данные отсутствуют
    private Integer count; // Количество записей
    private List<Result> result;

    @Override
    public String toString() {
        return "RosFinMonResponse{" +
                "found=" + found +
                ", count=" + count +
                ", result=" + result +
                ", status=" + status +
                ", inquiry=" + inquiry +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}