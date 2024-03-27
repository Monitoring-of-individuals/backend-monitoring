package ru.monitoring.dto;

import lombok.*;

import java.math.BigDecimal;

// Информация о запросе
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Inquiry {
    private BigDecimal price;
    private double balance;
    private int speed;
    private int attempts;
}
