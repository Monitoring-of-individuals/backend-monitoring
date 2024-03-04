package ru.monitoring.dto.gibdd;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TimeStamp {
    private Long timestamp;
    private String date;
}
