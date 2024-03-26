package ru.monitoring.user.service.security;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.monitoring.mapper.UserMapper;
import ru.monitoring.service.security.AuthenticationService;
import ru.monitoring.service.security.JwtService;
import ru.monitoring.service.security.UserAuthService;

import static org.mockito.Mockito.verifyNoMoreInteractions;

@RequiredArgsConstructor
@SpringBootTest(classes = {AuthenticationService.class, UserAuthService.class, JwtService.class,
        UserMapper.class, AuthenticationManager.class, PasswordEncoder.class,
        UserDetailsService.class})
class AuthenticationServiceTest {

    @MockBean
    UserDetailsService userDetailsService;
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

    @AfterEach
    void noMore() {
        verifyNoMoreInteractions(userAuthService, jwtService, userMapper, userDetailsService);
    }

    /*@SneakyThrows
    @Test
    void testSignUp() {
        SignUpUserRequestDto userDto = getSignUpUserDto();
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
    }*/

    /*@Test
    void testSignIn() {
        User user = getUser();
        String token = getToken();
        SignInUserRequestDto signInUserRequestDto = getSignInUserDto();

        when(userAuthService.userDetailsService()).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername(signInUserRequestDto.getEmail())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        JwtResponse jwtResponse = testedService.signIn(signInUserRequestDto);

        assertThat(jwtResponse.getToken()).isEqualTo(token);

        verify(userAuthService).userDetailsService();
        verify(userDetailsService).loadUserByUsername(signInUserRequestDto.getEmail());
        verify(jwtService).generateToken(user);
    }*/
}