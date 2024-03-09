package ru.monitoring.dto;


import lombok.*;

// Информация о запросе
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Inquiry {
    private float price;
    private double balance;
    private int speed;
    private int attempts;
}
