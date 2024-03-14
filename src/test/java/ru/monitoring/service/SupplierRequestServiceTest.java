package ru.monitoring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.monitoring.clients.ApiCloudClient;
import ru.monitoring.model.ApiCloudBalance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.monitoring.utils.Constants.API_CLOUD_TOKEN;

@ExtendWith(MockitoExtension.class)
class SupplierRequestServiceTest {

    private ApiCloudClient apiCloudClient;
    private SupplierRequestService service;

    @BeforeEach
    void beforeEach() {
        apiCloudClient = mock(ApiCloudClient.class);
        service = new SupplierRequestService(apiCloudClient);
    }

    @Test
    void getApiCloudBalance() {
        String balanceJson = """
                {"status":200,"balance":550,"message":null,"error":null}
                """;
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");
        paramMap.add("token", API_CLOUD_TOKEN);

        when(apiCloudClient.getApiCloudResponse("/apilk.php", paramMap))
                .thenReturn(balanceJson);

        ApiCloudBalance balance = service.getApiCloudBalance();

        assertEquals(200, balance.getStatus());
        assertEquals(550, balance.getBalance());
        assertNull(balance.getError());
        assertNull(balance.getMessage());
    }

    @Test
    void getApiCloudBalanceAndReturnNull() {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");
        paramMap.add("token", API_CLOUD_TOKEN);

        when(apiCloudClient.getApiCloudResponse("/apilk.php", paramMap))
                .thenReturn(null);

        ApiCloudBalance balance = service.getApiCloudBalance();

        assertEquals(0, balance.getStatus());
        assertEquals(0, balance.getBalance());
        assertNull(balance.getError());
        assertNull(balance.getMessage());
    }
}