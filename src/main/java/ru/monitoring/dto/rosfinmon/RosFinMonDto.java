package ru.monitoring.dto.rosfinmon;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RosFinMonDto {
    private Integer status;
    private String message;

    private Boolean found; // true - результаты найдены, false - данные отсутствуют
    private Integer count; // Количество записей
    private List<Result> result;
}
