package ru.monitoring.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.monitoring.dto.SignInUserRequestDto;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.dto.internal.UserDtoWithTokenInternal;
import ru.monitoring.mapper.UserMapper;
import ru.monitoring.model.User;
import ru.monitoring.repository.RevokedTokenRepository;
import ru.monitoring.service.IMailService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Сервис аутентификации, отвечающий за регистрацию и вход пользователей.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private static final String WELCOME_EMAIL_MESSAGE_TEMPLATE_HTML
            = "classpath:/templates/registration_done_email_template.html";
    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final RevokedTokenService revokedTokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RevokedTokenRepository revokedTokenRepository;
    private final IMailService mailService;
    private final TemplateEngine templateEngine;

    private final ResourceLoader resourceLoader;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.notifications.enabled}")
    private boolean shouldNotifyUponSignUp; // слать ли уведомление на почту об успешной регистрации

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param signUpUserRequestDto DTO для регистрации нового пользователя.
     * @return UserResponseDto DTO, содержащий информацию о зарегистрированном пользователе, включая JWT токен.
     */
    @Transactional
    public UserDtoWithTokenInternal signUp(SignUpUserRequestDto signUpUserRequestDto) {
        User user = userMapper.convertSignUpUserDtoToUser(signUpUserRequestDto);
        User returnedUser = userAuthService.create(user);
        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto(user);
        String jwt = jwtService.generateToken(returnedUser);
        UserDtoWithTokenInternal userDtoWithTokenInternal = UserDtoWithTokenInternal.builder()
                .userResponseDto(userResponseDto)
                .token(jwt)
                .build();
        // Отсылка уведомления на почту по необходимости
        Resource templateResource = resourceLoader.getResource(WELCOME_EMAIL_MESSAGE_TEMPLATE_HTML);
        if (shouldNotifyUponSignUp) {
            // Отошлём сообщение на почту, что регистрация прошла успешно
            byte[] messageHtmlEncoded;
            try {
                messageHtmlEncoded = StreamUtils.copyToByteArray(templateResource.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String message = new String(messageHtmlEncoded, StandardCharsets.UTF_8);
            Context context = new Context();
            context.setVariable("username", user.getFirstName());
            message = templateEngine.process("registration_done_email_template.html", context);
            mailService.sendMail(fromEmail, user.getEmail(), "Регистрация", message);
        }

        return userDtoWithTokenInternal;
    }

    /**
     * Аутентифицирует пользователя и возвращает JWT токен.
     *
     * @param signInUserRequestDto DTO для входа пользователя.
     * @return JwtResponse Объект ответа, содержащий JWT токен для аутентифицированного пользователя.
     */
    public UserDtoWithTokenInternal signIn(SignInUserRequestDto signInUserRequestDto,
                                           String oldToken) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInUserRequestDto.getEmail(),
                        signInUserRequestDto.getPassword()));

        UserDetails user = userAuthService.userDetailsService()
                .loadUserByUsername(signInUserRequestDto.getEmail());
        // Если нам передали старый токен, забаним его
        if (oldToken != null) {
            revokedTokenService.revokeToken(oldToken);
        }
        String jwt = jwtService.generateToken(user);

        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto((User) user);

        UserDtoWithTokenInternal userDtoWithTokenInternal = UserDtoWithTokenInternal.builder()
                .userResponseDto(userResponseDto)
                .token(jwt)
                .build();

        log.info("Пользователь {} успешно прошел авторизацию", user.getUsername());
        return userDtoWithTokenInternal;
    }

    /**
     * Отзыв токена аутентификацию пользователя и помещение его во временное хранилище Redis
     *
     * @param authToken
     */
    public void signOut(String authToken) {
        revokedTokenService.revokeToken(authToken);
    }
}
