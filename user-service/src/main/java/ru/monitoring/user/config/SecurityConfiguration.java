package ru.monitoring.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ru.monitoring.user.service.security.UserAuthService;

import java.util.List;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Конфигурация безопасности для Spring Security.
 * Включает в себя настройку CORS, аутентификации, доступа к эндпоинтам и сессий.
 */
@Configuration
@EnableWebSecurity // Включение поддержки безопасности веб-секции.
@EnableMethodSecurity // Включение аннотаций для методов безопасности.
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserAuthService userAuthService;

    /**
     * Определяет основную цепочку фильтров безопасности.
     *
     * @param http Конфигуратор безопасности HTTP.
     * @return сконфигурированная цепочка фильтров безопасности.
     * @throws Exception исключение в случае ошибок конфигурации.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(request -> request.dispatcherTypeMatchers(FORWARD, ERROR)
                        .permitAll()
                        .requestMatchers("/auth/sign-out")
                        .authenticated()
                        .requestMatchers("/auth/helloAuth")
                        .authenticated()
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/api")
                        .authenticated()
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Создает бин кодировщика паролей.
     *
     * @return объект кодировщика паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает провайдер аутентификации для управления процессом аутентификации.
     *
     * @return объект провайдера аутентификации.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userAuthService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Создает менеджер аутентификации для обработки проверки подлинности.
     *
     * @param config Конфигурация аутентификации.
     * @return объект менеджера аутентификации.
     * @throws Exception исключение в случае ошибок конфигурации.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
