package com._on1bet.authservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com._on1bet.authservice.SecurityTestConfig;
import com._on1bet.authservice.service.RegisterService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(SecurityTestConfig.class) 
@ActiveProfiles("test")
class RegisterControllerTest {

        @Autowired
        private WebTestClient webTestClient;

        @MockitoBean
        RegisterService registerService; 

        @Test
        void invalidEndpoint() throws Exception {
                webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockUser()).post()
                                .uri("/api/invalid/endpoint")
                                .exchange()
                                .expectStatus().isNotFound();
        }

        @Test
        void testFirstTimeMobileNoRegisterHandlerWithEmptyValues() throws Exception {
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
