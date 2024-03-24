package ru.monitoring.dto.mvd;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PassportCheckDto {
    private Integer status;
    private String message;

    private Integer result;
    private String info;
}
