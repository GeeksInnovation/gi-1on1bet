package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.model.response.CountryCodeListResponse;
import com._on1bet.authservice.service.UtilCountryService;
import com._on1betutils.utils1on1bet._on1BetResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/util/")
@Slf4j
public class UtilCountryController {

    private final UtilCountryService utilCountryService;

    public UtilCountryController(UtilCountryService utilCountryService){
        this.utilCountryService = utilCountryService;
    }
    @GetMapping("get/country/code")
    public Mono<_on1BetResponse<CountryCodeListResponse>> getCountryCodeList() {
        log.info("Fetch country code list handler starts");
        return utilCountryService.getAllCountryCode();
    }
    
    
}
