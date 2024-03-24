package ru.monitoring.client.imlp;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.monitoring.client.IReportServiceClient;
import ru.monitoring.dto.PersonInfoRequestDto;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static ru.monitoring.utils.Constants.TIMEOUT;

@Component
public class ReportServiceClient implements IReportServiceClient {
    private final WebClient webClient;

    public ReportServiceClient(@Value("${app.report.service.url}") String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(TIMEOUT))
                .doOnConnected(connection -> connection.addHandlerLast(
                                new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public String getReport(PersonInfoRequestDto personInfoRequestDto) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/report")
                        .build())
                .bodyValue(personInfoRequestDto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
