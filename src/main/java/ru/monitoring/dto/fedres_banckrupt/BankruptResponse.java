package ru.monitoring.dto.fedres_banckrupt;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankruptResponse extends ResponseDto {

    private Integer totalCount; // Количество найденных записей
    private List<Rez> rez;
    // В случае если не нашли
    private Integer num; // Количество найденных записей
}