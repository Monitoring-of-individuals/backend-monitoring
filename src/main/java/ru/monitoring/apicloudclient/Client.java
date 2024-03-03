package ru.monitoring.apicloudclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static ru.monitoring.utils.Constants.API_CLOUD_URL;
import static ru.monitoring.utils.Constants.TIMEOUT;


@Service
public class Client {
    private final WebClient client;

    public Client(String apiCloudUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .responseTimeout(Duration.ofMillis(TIMEOUT))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
        ;
        this.client = WebClient.builder()
                .baseUrl(apiCloudUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }


    // http://localhost:8080/api/nalog.php?type=npd&inn=123456789012&token=53ba1b7a55abbа14aa97eff3a5220792
    public SelfEmplResponse getSelfEmplByInn(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service)
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(SelfEmplResponse.class)
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(500)))
                .block();
    }

    // http://localhost:8080/api/nalog.php?type=inn&lastname=Петров&firstname=Петр&birthdate=15.07.1980&serianomer=9876543210&token=53ba1b7a55abbа14aa97eff3a5220792
    public InnResponse getInnByFioAndPassportAndBirthday(String service, MultiValueMap paramMap) {

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api")
                        .path(service)
                        .queryParams(paramMap)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(InnResponse.class)
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(500)))
                .block();
    }

     public static void main(String[] args) {

         final Client webClient = new Client(API_CLOUD_URL);

         MultiValueMap paramMap = new LinkedMultiValueMap();
         paramMap.add("type", "inn");
         paramMap.add("lastname", "Петров");
         paramMap.add("firstname", "Петр");
         paramMap.add("birthdate", "15.07.1980");
         paramMap.add("serianomer", "9876543210");
         paramMap.add("token", "53ba1b7a55abbа14aa97eff3a5220792");

         InnResponse innResponse = webClient.getInnByFioAndPassportAndBirthday("/nalog.php", paramMap);
         System.out.println(innResponse);
         System.out.println();
         String inn = innResponse.getInn();

         MultiValueMap paramMap1 = new LinkedMultiValueMap();
         paramMap1.add("type", "npd");
         paramMap1.add("inn", inn);
         paramMap1.add("token", "53ba1b7a55abbа14aa97eff3a5220792");

         for (int i = 1; i <= 3; i++) {
             SelfEmplResponse result = webClient.getSelfEmplByInn("/nalog.php", paramMap1);
             System.out.println(result);
         }


//        for (int i = 1; i <= 6; i++) {
//            Object result = webClient.getSelfEmplByInn("npd", "123456789012", "53ba1b7a55abbа14aa97eff3a5220792");
//            System.out.println(result);
//
//            LinkedHashMap result1 = (LinkedHashMap) result;
//
//            if (result1.get("status").equals(200)) {
//                System.out.println("НАШЛИ");
//            } else if (result1.get("status").equals(404)) {
//                System.out.println("TIME_MAX_CONNECT");
//            }
//        }
     }
}
