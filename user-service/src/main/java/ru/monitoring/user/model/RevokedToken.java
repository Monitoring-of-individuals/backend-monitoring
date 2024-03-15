package ru.monitoring.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Date;

@RedisHash(value = "RevokedToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevokedToken {
    @Id
    private String token;
    private String email;
    private Date iat; // Время выдачи токена
    private Date exp; // Время истечения токена@
    @TimeToLive
    private Long ttl; // Время жизни в секундах
}
