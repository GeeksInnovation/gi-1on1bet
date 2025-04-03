package com._on1bet.authservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

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
        void test_invalidEndpoint_Failure() throws Exception {
                webTestClient
                                .mutateWith(SecurityMockServerConfigurers.mockUser()).post()
                                .uri("/api/invalid/endpoint")
                                .exchange()
                                .expectStatus().isNotFound();
        }

        @Test
        void test_firstTimeMobileNoRegisterHandler_Failure_EmptyValues() throws Exception {
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
        void test_firstTimeMobileNoRegisterHandler_Success() throws Exception {
                webTestClient.post()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/api/v1/register/verify/mobileno")
                                        .queryParam("mobileno", "9876543210")
                                        .queryParam("countrycode", "5")
                                        .build())
                                .exchange()
                                .expectStatus().isOk();
        }

        @Test
        void test_saveUserHandler_Success() throws Exception {
                
                String requestBody = """
                        {
                                "mobileno": "9876543210",
                                "countrycode": "5"
                        }
                        """;

                webTestClient.post()
                                .uri("/api/v1/register/save/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(requestBody))
                                .exchange()
                                .expectStatus().isOk();
        }

}
