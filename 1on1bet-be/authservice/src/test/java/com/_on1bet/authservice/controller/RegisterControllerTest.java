package com._on1bet.authservice.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com._on1bet.authservice.service.RegisterService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegisterControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    RegisterService registerService;

    @Test
    void invalidEndpoint() throws Exception {
        webTestClient.post()
                .uri("/api/invalid/endpoint")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFirstTimeMobileNoRegisterHandlerWithInValidValues() throws Exception {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/register/verify/mobileno")
                        .queryParam("mobileno", "")
                        .queryParam("countrycode", "")
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void testFirstTimeMobileNoRegisterHandlerWithValidValues() throws Exception {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/register/verify/mobileno")
                        .queryParam("mobileno", "9876543210")
                        .queryParam("countrycode", "5")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}
