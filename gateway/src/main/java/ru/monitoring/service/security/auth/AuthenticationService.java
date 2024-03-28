package ru.monitoring.service.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.monitoring.dto.SignInUserRequestDto;
import ru.monitoring.dto.SignUpUserRequestDto;
import ru.monitoring.dto.UserResponseDto;
import ru.monitoring.dto.internal.UserDtoWithTokenInternal;
import ru.monitoring.mapper.UserMapper;
import ru.monitoring.model.ConfirmationCode;
import ru.monitoring.model.PendingVerificationUser;
import ru.monitoring.model.User;
import ru.monitoring.repository.ConfirmationCodeRepository;
import ru.monitoring.service.mail.IMailService;
import ru.monitoring.service.security.token.JwtService;
import ru.monitoring.service.security.token.RevokedTokenService;
import ru.monitoring.service.user.impl.PendingVerificationUserService;

import java.security.SecureRandom;

/**
 * Сервис аутентификации, отвечающий за регистрацию и вход пользователей.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final PendingVerificationUserService pendingVerificationUserService;
    private final RevokedTokenService revokedTokenService;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final IMailService mailService;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.notifications.enabled}")
    private boolean shouldNotifyUponSignUp; // слать ли уведомление на почту об успешной регистрации
    @Value("${app.ez.registration.enabled}")
    private boolean ezRegistration; // Включена ли тестовая регистрация

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param signUpUserRequestDto DTO для регистрации нового пользователя.
     */
    @Transactional
    public void signUp(SignUpUserRequestDto signUpUserRequestDto) {
        // Сохраняем пользователя во временную бд
        PendingVerificationUser pendingVerificationUser
                = userMapper.convertSignUpUserDtoToPendingVerificationUser(signUpUserRequestDto);
        pendingVerificationUserService.save(pendingVerificationUser);

        // Генерируем случайный 6 значный код
        SecureRandom random = new SecureRandom();
        int randomCode = random.nextInt(900000)
                + 100000; // 100000 (включительно) до 999999 (включительно)
        // Сохраняем code в БД Редис с кодами
        ConfirmationCode code = ConfirmationCode.builder()
                .email(pendingVerificationUser.getEmail())
                .code(randomCode)
                .build();
        confirmationCodeRepository.save(code);
        // Отправим на почту.
        mailService.sendConfirmationCode(fromEmail, pendingVerificationUser.getEmail(),
                "Подтверждение почты", pendingVerificationUser.getFirstName(),
                String.valueOf(code.getCode()));
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
     * @param authToken токен
     */
    public void signOut(String authToken) {
        revokedTokenService.revokeToken(authToken);
    }

    private UserDtoWithTokenInternal oldSignUp(SignUpUserRequestDto signUpUserRequestDto) {
        User user = userMapper.convertSignUpUserDtoToUser(signUpUserRequestDto);
        User returnedUser = userAuthService.create(user);
        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto(user);
        String jwt = jwtService.generateToken(returnedUser);
        UserDtoWithTokenInternal userDtoWithTokenInternal = UserDtoWithTokenInternal.builder()
                .userResponseDto(userResponseDto)
                .token(jwt)
                .build();
        // Отсылка уведомления на почту по необходимости
        if (shouldNotifyUponSignUp) {
            mailService.sendSuccessfulRegistrationMail(fromEmail, returnedUser.getEmail(),
                    returnedUser.getFirstName());
        }

        return userDtoWithTokenInternal;
    }
}
