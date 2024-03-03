package ru.monitoring.dto.nalog;

import lombok.*;
import ru.monitoring.dto.Inquiry;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InnResponse {
    private int status;
    private boolean found;
    private String inn;
    private String message;
    private Inquiry inquiry;
    private String error;
    private String errormsg;


}
