package ru.monitoring.dto.rosfinmon;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RosFinMonResponse extends ResponseDto {

    private Boolean found; // true - результаты найдены, false - данные отсутствуют
    private Integer count; // Количество записей
    private List<Result> result;
}