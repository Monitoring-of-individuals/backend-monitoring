package ru.monitoring.dto.gibdd;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GibddResponse extends ResponseDto {

    private Doc doc;
    private List<Deprivation> decis; // Ифнормаци о лишениях
    private Boolean found; // Найдено удостоверение или нет, true - найдено | false - не найдено
    private Cache cache; // Если вывод результата из кэша

    @Override
    public String toString() {
        return "GibddResponse{" +
                "doc=" + doc +
                ", decis=" + decis +
                ", found=" + found +
                ", cache=" + cache +
                ", status=" + status +
                ", inquiry=" + inquiry +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}