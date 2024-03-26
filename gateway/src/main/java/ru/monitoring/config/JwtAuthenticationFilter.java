package ru.monitoring.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.monitoring.service.security.JwtService;
import ru.monitoring.service.security.UserAuthService;

import java.io.IOException;

/**
 * Фильтр для аутентификации с использованием JWT (JSON Web Tokens).
 * Проверяет наличие JWT в запросах и аутентифицирует пользователя при его наличии и валидности.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserAuthService userAuthService;

    /**
     * Фильтрует каждый входящий запрос. Извлекает и проверяет JWT.
     *
     * @param request     Запрос от клиента.
     * @param response    Ответ сервера.
     * @param filterChain Цепочка дальнейших фильтров.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AuthToken".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    log.info("jwt from cookie {}", jwt);
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtService.extractUserName(jwt);

        // Установка контекста аутентификации, если токен валиден и пользователь еще не аутентифицирован.
        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails = userAuthService.userDetailsService()
                    .loadUserByUsername(username);

            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        // Продолжение обработки запроса.
        filterChain.doFilter(request, response);
    }
}
