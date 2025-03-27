package com._on1bet.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com._on1bet.authservice.model.response.CountryCodeListResponse;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UtilCountryServiceTest {

    @InjectMocks
    UtilCountryService utilCountryService;

    @Mock
    UtilRepo utilRepo;

    @Mock
    _on1BetResponseBuilder _on1betResponseBuilder;

    @Test
    void test_getAllCountryCode_Success() {

        CountryCodeListResponse mockResponse = new CountryCodeListResponse(List.of());

        when(utilRepo.fetchCountryCodeDetails()).thenReturn(Flux.empty());
        when(_on1betResponseBuilder.buildSuccessResponse(any()))
                .thenReturn(new _on1BetResponse<>(true, null, mockResponse));

        StepVerifier.create(utilCountryService.getAllCountryCode())
        .assertNext((reponse) -> {
            assertEquals(true, reponse.getServiceStatus());
        }).verifyComplete();

    }

}
