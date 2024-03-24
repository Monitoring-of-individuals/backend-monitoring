package ru.monitoring.dto.fedres_banckrupt;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BankruptDto {

    private Integer status;
    private String message;

    private Integer totalCount; // Количество найденных записей
    private List<Rez> rez;
    private Integer num; // Количество найденных записей
}
