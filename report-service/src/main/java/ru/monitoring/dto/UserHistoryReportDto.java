package ru.monitoring.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryReportDto {

    private Boolean isSuccess; // если хотя бы одно из полей выше заполнено не null
    private LocalDateTime reportDateTime;

    private String personLastName;
    private String personFirstName;
    private String personFatherName;

    private String reportJsonString;
}
