package http.error;

import com.example.expensetracking.domain.crud.CurrencyConvertable;
import com.example.expensetracking.infrastructure.crud.http.ExchangeRateClientConfig;
import com.example.expensetracking.infrastructure.crud.http.ExchangeRateClientConfigurationProperties;
import com.example.expensetracking.infrastructure.crud.http.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class ExchangeRateClientRestTemplateIntegrationTestConfig extends ExchangeRateClientConfig {

    public CurrencyConvertable exchangeRateClientForTest(int port, int connectionTimeout, int readTimeout) {
        ExchangeRateClientConfigurationProperties properties = new ExchangeRateClientConfigurationProperties(
                "http://localhost:" + port,
                port,
                connectionTimeout,
                readTimeout
        );
        RestTemplate restTemplate = restTemplateTestConfig(restTemplateResponseErrorHandler(), connectionTimeout, readTimeout);

        return exchangeRateRestTemplateClientTestConfig(restTemplate, properties.uri(), properties.port());
    }

    private RestTemplate restTemplateTestConfig(RestTemplateResponseErrorHandler errorHandler, int connectionTimeout, int readTimeout) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}