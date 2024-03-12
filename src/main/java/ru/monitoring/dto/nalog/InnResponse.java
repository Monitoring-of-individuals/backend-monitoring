package ru.monitoring.dto.nalog;

import lombok.*;
import ru.monitoring.dto.ResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InnResponse extends ResponseDto {

    private Boolean found;
    private String inn;
}