package ru.monitoring.dto.nalog;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SelfEmplDto {
    private Integer status;
    private String message;

    private Boolean found;
    private String inn;
    private String date;
    private Integer npd;
}
