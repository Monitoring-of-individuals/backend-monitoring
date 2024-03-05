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
import ru.monitoring.user.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto signUp(SignUpUserDto signUpUserDto) {
        User user = userMapper.convertSignUpUserDtoToUser(signUpUserDto);
        User returnedUser = userAuthService.create(user);
        UserResponseDto userResponseDto = userMapper.convertUserToUserResponseDto(user);
        String jwt = jwtService.generateToken(returnedUser);
        userResponseDto.setToken(jwt);
        return userResponseDto;
    }

    public JwtResponse signIn(SignInUserDto signInUserDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInUserDto.getEmail(),
                signInUserDto.getPassword()
        ));

        UserDetails user = userAuthService.userDetailsService().loadUserByUsername(signInUserDto.getEmail());

        String jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }
}