package ru.monitoring.dto.fedres_banckrupt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rez {
    private Guid guid;
    private Fio fio;
    private Inn inn;
    private Snils snils;
    private Category category;
    private Region region;
    private ArbitrManagerFio arbitrManagerFio;
    private Address address;
    private LastLegalCasenNumber lastLegalCasenNumber;
    private Status status;
    private Description description;
    private UpdateDate updateDate;
    private IsActive isActive;
}

