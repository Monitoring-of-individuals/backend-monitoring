package ru.monitoring.clients;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;
import ru.monitoring.exceptions.ApiCloudResponseException;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static ru.monitoring.utils.Constants.TIMEOUT;

@Slf4j
@Service
public class ApiCloudClient {
    private final WebClient client;

    public ApiCloudClient(@Value("${apicloud.url}") String apiCloudUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(TIMEOUT))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
        this.client = WebClient.builder()
                .baseUrl(apiCloudUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    /**
     * Теперь в клиенте один универсальный метод.<p>
     * В случае ответа от поставщика со статусом 200, возвращает строку с JSON объектом.<p>
     * В случае ответа со статусом 4хх, 5хх, например если сервер поставщика недоступен, ошибка в URL и т.д.,
     * клиент возвращает null.
     */
    public String getApiCloudResponse(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /bankrot.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ApiCloudResponseException(
                        error.statusCode() + " " + error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ApiCloudResponseException(
                        error.statusCode() + " " + error.getClass().toString())))
                .bodyToMono(String.class)
                .doOnError(error -> {
                    log.error("Произошла ошибка: {}, подробности: {}, стэктрейс: {}", error.getClass(), error.getMessage(), error.getStackTrace());
                })
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(1000)))
                .doOnError(error -> {
                    log.error("Произошла ошибка: {}, с сообщением: {}, причина: {}, стэктрейс: {}", error.getClass(),
                            error.getMessage(), error.getCause(), error.getStackTrace());
                })
                .onErrorResume(ex -> Mono.empty())
                .block();
    }
}
