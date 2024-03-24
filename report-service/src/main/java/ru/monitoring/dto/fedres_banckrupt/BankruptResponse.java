package ru.monitoring.dto.fedres_banckrupt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankruptResponse extends ResponseDto {

    private Integer totalCount; // Количество найденных записей
    private List<Rez> rez;
    private Integer num; // Количество найденных записей

    @Override
    public String toString() {
        return "BankruptResponse{" + "totalCount=" + totalCount + ", rez=" + rez + ", num=" + num
                + ", status=" + status + ", inquiry=" + inquiry + ", error='" + error + '\''
                + ", message='" + message + '\'' + ", errorMsg='" + errorMsg + '\'' + '}';
    }
}