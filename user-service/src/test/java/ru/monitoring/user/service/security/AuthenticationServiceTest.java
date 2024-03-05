package ru.monitoring.user.service.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.monitoring.user.dto.JwtResponse;
import ru.monitoring.user.dto.SignInUserDto;
import ru.monitoring.user.dto.SignUpUserDto;
import ru.monitoring.user.dto.UserResponseDto;
import ru.monitoring.user.mapper.UserMapper;
import ru.monitoring.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ru.monitoring.user.CreatorTestEntities.getSignInUserDto;
import static ru.monitoring.user.CreatorTestEntities.getSignUpUserDto;
import static ru.monitoring.user.CreatorTestEntities.getToken;
import static ru.monitoring.user.CreatorTestEntities.getUser;
import static ru.monitoring.user.CreatorTestEntities.getUserResponseDto;
import static ru.monitoring.user.CreatorTestEntities.getUserWithoutId;

@RequiredArgsConstructor
@SpringBootTest(classes = {AuthenticationService.class, UserAuthService.class, JwtService.class, UserMapper.class, AuthenticationManager.class, PasswordEncoder.class, UserDetailsService.class})
class AuthenticationServiceTest {

    @MockBean
    private UserAuthService userAuthService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService testedService;

    @MockBean
    UserDetailsService userDetailsService;

    @AfterEach
    void noMore() {
        verifyNoMoreInteractions(userAuthService, jwtService, userMapper, userDetailsService);
    }


    @SneakyThrows
    @Test
    void testSignUp() {
        SignUpUserDto userDto = getSignUpUserDto();
        User user = getUserWithoutId();
        UserResponseDto responseDto = getUserResponseDto();
        String token = getToken();

        when(userMapper.convertSignUpUserDtoToUser(userDto)).thenReturn(user);
        when(userMapper.convertUserToUserResponseDto(user)).thenReturn(responseDto);
        when(userAuthService.create(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        UserResponseDto response = testedService.signUp(userDto);

        assertThat(response).isEqualTo(responseDto);
        assertThat(responseDto.getToken()).isEqualTo(token);

        verify(userMapper).convertSignUpUserDtoToUser(userDto);
        verify(userAuthService).create(user);
        verify(userMapper).convertUserToUserResponseDto(user);
        verify(jwtService).generateToken(user);
    }

    @Test
    void testSignIn() {
        User user = getUser();
        String token = getToken();
        SignInUserDto signInUserDto = getSignInUserDto();

        when(userAuthService.userDetailsService()).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername(signInUserDto.getEmail())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        JwtResponse jwtResponse = testedService.signIn(signInUserDto);

        assertThat(jwtResponse.getToken()).isEqualTo(token);

        verify(userAuthService).userDetailsService();
        verify(userDetailsService).loadUserByUsername(signInUserDto.getEmail());
        verify(jwtService).generateToken(user);
    }
}