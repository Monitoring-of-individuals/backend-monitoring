package ru.monitoring.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.dto.SignInUserRequestDto;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.service.security.AuthenticationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public UserResponseDto signUp(@RequestBody @Valid SignUpUserRequestDto signUpUserRequestDto) {
        log.info("Получен запрос на регистрацию пользователя email {} ",
                signUpUserRequestDto.getEmail());
        return authenticationService.signUp(signUpUserRequestDto);
    }

    @PostMapping("/sign-in")
    public UserResponseDto signIn(@RequestBody @Valid SignInUserRequestDto signInUserRequestDto) {
        return authenticationService.signIn(signInUserRequestDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(
            @RequestHeader(value = "Authorization", required = false) String authToken) {
        authenticationService.signOut(authToken);
        return ResponseEntity.ok("Successfully signed out.");
    }
}
