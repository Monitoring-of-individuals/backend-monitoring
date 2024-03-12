package ru.monitoring.user.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.monitoring.user.dto.JwtResponse;
import ru.monitoring.user.dto.SignInUserDto;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.mapper.UserMapper;
import ru.monitoring.user.model.RevokedToken;
import ru.monitoring.user.model.User;
import ru.monitoring.user.repository.RevokedTokenRepository;

import java.util.Date;

/**
 * Сервис аутентификации, отвечающий за регистрацию и вход пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RevokedTokenRepository revokedTokenRepository;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param signUpUserDto DTO для регистрации нового пользователя.
     * @return UserResponseDto DTO, содержащий информацию о зарегистрированном пользователе, включая JWT токен.
     */
    public UserResponseDto signUp(SignUpUserDto signUpUserDto) {
        User user = userMapper.convertSignUpUserDtoToUser(signUpUserDto);
        User returnedUser = userAuthService.create(user);
        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto(user);
        String jwt = jwtService.generateToken(returnedUser);
        userResponseDto.setToken(jwt);
        return userResponseDto;
    }

    /**
     * Аутентифицирует пользователя и возвращает JWT токен.
     *
     * @param signInUserDto DTO для входа пользователя.
     * @return JwtResponse Объект ответа, содержащий JWT токен для аутентифицированного пользователя.
     */
    public JwtResponse signIn(SignInUserDto signInUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInUserDto.getEmail(),
                        signInUserDto.getPassword()));

        UserDetails user = userAuthService.userDetailsService()
                .loadUserByUsername(signInUserDto.getEmail());

        String jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }

    /**
     * Отзыв токена аутентификацию пользователя и помещение его во временное хранилище Redis
     *
     * @param authToken
     */
    public void signOut(String authToken) {
        // Обрезаем префикс и получаем токен
        String jwt = authToken.substring(BEARER_PREFIX.length());

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
