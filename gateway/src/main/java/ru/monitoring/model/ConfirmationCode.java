package ru.monitoring.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "ConfirmationCode", timeToLive = 300) //время жизни кода 5 минут
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationCode {
    @Id
    private String email;
    private Integer code;
}
