package ru.monitoring.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "PendingVerificationUser", timeToLive = 900) //900 секунд, 15 минут
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingVerificationUser {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
