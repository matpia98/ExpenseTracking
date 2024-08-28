package com.example.expensetracking.infrastructure.crud.http;

import com.example.expensetracking.domain.crud.CurrencyConvertable;
import com.example.expensetracking.domain.crud.dto.ExchangeRateResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@Log4j2
class ExchangeRateClient implements CurrencyConvertable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    private static final String BASE_CURRENCY = "PLN";


    public ExchangeRateResponse fetchExchangeRates() {
        log.info("Started fetching exchange rates");
        String urlForService = getUrlForService("/latest/" + BASE_CURRENCY);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .toUriString();
        try {
            ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            ExchangeRateResponse body = response.getBody();
            if (body == null) {
                log.info("Response Body was null");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("Response body returned: " + body);
            return body;
        } catch (ResourceAccessException | IllegalArgumentException e) {
            log.error("Error while fetching exchange rates: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUrlForService(String service) {
        return uri + service;
    }
}