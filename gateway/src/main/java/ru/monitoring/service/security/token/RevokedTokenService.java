package ru.monitoring.service.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitoring.model.RevokedToken;
import ru.monitoring.repository.RevokedTokenRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
    private final RevokedTokenRepository revokedTokenRepository;
    private final JwtService jwtService;

    public void revokeToken(String jwt) {
        // Добавляем токен в Redis хранилище забаненых ключей
        Date iat = jwtService.extractIssuedAt(jwt);
        Date exp = jwtService.extractExpiration(jwt);
        String email = jwtService.extractUserName(jwt);
        long ttl = (exp.getTime() - System.currentTimeMillis()) / 1000; // TTL в секундах

        RevokedToken revokedToken = RevokedToken.builder()
                .token(jwt)
                .email(email)
                .iat(iat)
                .exp(exp)
                .ttl(ttl)
                .build();
        revokedTokenRepository.save(revokedToken);
    }
}
