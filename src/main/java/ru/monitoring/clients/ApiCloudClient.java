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
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.exceptions.ClentError4xxException;
import ru.monitoring.exceptions.ServerError5xxException;

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

    //http://localhost:8080/api/fssp.php?type=physical&lastname=Иванов&firstname=Иван&secondname=Иванович&birthdate=31.03.1995&region=-1&token=53ba1b7a55abbа14aa97eff3a5220792
    public FsspResponse getFsspEnfProcessings(String service, MultiValueMap paramMap) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /fssp.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(FsspResponse.class)
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

    // http://localhost:8080/api/nalog.php?type=inn&lastname=Петров&firstname=Петр&birthdate=15.07.1980&serianomer=9876543210&token=53ba1b7a55abbа14aa97eff3a5220792
    public InnResponse getInnByFioAndPassportAndBirthday(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /nalog.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(InnResponse.class)
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

    // http://localhost:8080/api/nalog.php?type=npd&inn=123456789012&token=53ba1b7a55abbа14aa97eff3a5220792
    public SelfEmplResponse getSelfEmplByInn(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /nalog.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(SelfEmplResponse.class)
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

    //http://localhost:8080/api/mvd.php?type=chekpassport&seria=0000&nomer=000000&token=53ba1b7a55abbа14aa97eff3a5220792
    public PassportCheckResponse getPassportCheck(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /mvd.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(PassportCheckResponse.class)
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

    //http://localhost:8080/api/gibdd.php?type=driver&serianomer=1234567890&date=07.11.2014&token=53ba1b7a55abbа14aa97eff3a5220792
    public GibddResponse getDriverIdCheck(String service, MultiValueMap paramMap) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /gibdd.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(GibddResponse.class)
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

    //http://localhost:8080/api/fedsfm.php?type=terextr&search=АБАКАРОВ ШАМИЛЬ БАГОМЕДОВИЧ&token=53ba1b7a55abbа14aa97eff3a5220792
    public RosFinMonResponse getTerrorExtrCheck(String service, MultiValueMap paramMap) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /fedsfm.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(RosFinMonResponse.class)
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

    //http://localhost:8080/api/bankrot.php?type=searchString&string=123456789012&legalStatus=fiz&token=53ba1b7a55abbа14aa97eff3a5220792
    public BankruptResponse getBankruptCheck(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service) // /bankrot.php
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new ClentError4xxException(error.getClass().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new ServerError5xxException(error.getClass().toString())))
                .bodyToMono(BankruptResponse.class)
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
