package ru.monitoring.user.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.monitoring.user.dto.JwtResponse;
import ru.monitoring.user.dto.SignInUserDto;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.mapper.UserMapper;
import ru.monitoring.user.model.RevokedToken;
import ru.monitoring.user.model.User;
import ru.monitoring.user.repository.RevokedTokenRepository;
import ru.monitoring.user.service.IMailService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Сервис аутентификации, отвечающий за регистрацию и вход пользователей.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private static final String templateHtml
            = "user-service/src/main/resources/templates/registration_done_email_template.html";
    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RevokedTokenRepository revokedTokenRepository;
    private final IMailService mailService;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.notifications.enabled}")
    private boolean isNotify; // слать ли уведомление на почту об успешной регистрации

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param signUpUserDto DTO для регистрации нового пользователя.
     * @return UserResponseDto DTO, содержащий информацию о зарегистрированном пользователе, включая JWT токен.
     */
    @Transactional
    public UserResponseDto signUp(SignUpUserDto signUpUserDto) {
        User user = userMapper.convertSignUpUserDtoToUser(signUpUserDto);
        User returnedUser = userAuthService.create(user);
        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto(user);
        String jwt = jwtService.generateToken(returnedUser);
        userResponseDto.setToken(jwt);


        // Отсылка уведомления на почту по необходимости
        if (isNotify) {
            // Отошлём сообщение на почту, что регистрация прошла успешно
            byte[] messageHtmlEncoded;
            try {
                messageHtmlEncoded = Files.readAllBytes(Paths.get(templateHtml));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String message = new String(messageHtmlEncoded, StandardCharsets.UTF_8);
            Context context = new Context();
            context.setVariable("username", user.getFirstName());
            message = templateEngine.process("registration_done_email_template.html", context);
            mailService.sendMail(fromEmail, user.getEmail(), "Регистрация", message);
        }

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
        log.info("Пользователь {} успешно прошел авторизацию", user.getUsername());
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
