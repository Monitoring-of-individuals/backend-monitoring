package ru.monitoring.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import ru.monitoring.dto.internal.UserDtoWithTokenInternal;

@Service
@RequiredArgsConstructor
public class AuthCookieService {
    public ResponseCookie getAuthCookie(UserDtoWithTokenInternal userDtoWithTokenInternal) {
        return ResponseCookie.from("AuthToken", userDtoWithTokenInternal.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
    }
}
