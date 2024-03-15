package ru.monitoring.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.monitoring.user.dto.JwtResponse;
import ru.monitoring.user.dto.SignInUserDto;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.service.security.AuthenticationService;

@Tag(name = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@RequestBody @Valid SignUpUserDto signUpUserDto) {
        log.info("Получен запрос на регистрацию пользователя email {} ", signUpUserDto.getEmail());
        return authenticationService.signUp(signUpUserDto);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(
                            implementation = ErrorHandler.ErrorResponse.class)))})})
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody @Valid SignInUserDto signInUserDto) {
        return authenticationService.signIn(signInUserDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(
            @RequestHeader(value = "Authorization", required = false) String authToken) {
        authenticationService.signOut(authToken);
        return ResponseEntity.ok("Successfully signed out.");
    }
}