package ru.monitoring.clients;

import com.alibaba.fastjson.JSON;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.monitoring.model.ApiCloudBalance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApiCloudClientTest {

    public static MockWebServer mockWebServer;
    private ApiCloudClient apiCloudClient;

    @BeforeEach
    void initialize() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseURL = String.format("http://localhost:%s", mockWebServer.getPort());
        apiCloudClient = new ApiCloudClient(baseURL);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getApiCloudBalanceReturnsBalance() throws InterruptedException {
        ApiCloudBalance mockBalance = new ApiCloudBalance(200, 521, null, null);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JSON.toJSONString(mockBalance)));

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");

        String str = apiCloudClient.getApiCloudResponse("/apilk.php", paramMap);

        assertEquals(200, JSON.parseObject(str, ApiCloudBalance.class)
                .getStatus());
        assertEquals(521, JSON.parseObject(str, ApiCloudBalance.class)
                .getBalance());
        assertNull(JSON.parseObject(str, ApiCloudBalance.class)
                .getError());
        assertNull(JSON.parseObject(str, ApiCloudBalance.class)
                .getMessage());

        RecordedRequest request = mockWebServer.takeRequest();

        assertEquals("application/json", request.getHeader("Content-Type"),
                "Content-Type != application/json");
        assertEquals("GET", request.getMethod(), "Статус ответа не 200");
        assertEquals("/api/apilk.php?type=balance", request.getPath(), "неправильный путь");
    }

    @Test
    void getApiCloudBalanceReturnsNullWhenException() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(520)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");

        String str = apiCloudClient.getApiCloudResponse("/apilk.php", paramMap);

        assertNull(str, "При ошибке клиент должен вернуть null");
    }
}