package ru.monitoring.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.dto.SignInUserRequestDto;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.dto.internal.UserDtoWithTokenInternal;
import ru.monitoring.service.security.AuthCookieService;
import ru.monitoring.service.security.AuthenticationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private final AuthCookieService authCookieService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(
            @RequestBody @Valid SignUpUserRequestDto signUpUserRequestDto) {
        log.info("Получен запрос на регистрацию пользователя email {}",
                signUpUserRequestDto.getEmail());

        UserDtoWithTokenInternal userResponseDto = authenticationService.signUp(
                signUpUserRequestDto);
        ResponseCookie authCookie = authCookieService.getAuthCookie(userResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED) // Здесь мы меняем статус на 201
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(userResponseDto.getUserResponseDto());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(
            @RequestBody @Valid SignInUserRequestDto signInUserRequestDto,
            @CookieValue(value = "AuthToken", required = false) String oldToken) {
        UserDtoWithTokenInternal userDtoWithTokenInternal = authenticationService.signIn(
                signInUserRequestDto, oldToken);
        ResponseCookie authCookie = authCookieService.getAuthCookie(userDtoWithTokenInternal);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(userDtoWithTokenInternal.getUserResponseDto());
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(
            @CookieValue(value = "AuthToken", required = true) String authToken) {
        authenticationService.signOut(authToken);
        return ResponseEntity.ok("Successfully signed out.");
    }
}
