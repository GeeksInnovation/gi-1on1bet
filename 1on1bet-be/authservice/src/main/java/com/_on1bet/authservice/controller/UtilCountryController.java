package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.model.response.CountryCodeListResponse;
import com._on1bet.authservice.service.UtilCountryService;
import com._on1betutils.utils1on1bet._on1BetResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/util/")
public class UtilCountryController {

    private final UtilCountryService utilCountryService;

    public UtilCountryController(UtilCountryService utilCountryService){
        this.utilCountryService = utilCountryService;
    }
    @GetMapping("get/country/code")
    public ResponseEntity<_on1BetResponse<CountryCodeListResponse>> getCountryCodeList() {
        return ResponseEntity.ok(utilCountryService.getAllCountryCode());
    }
    
    
}
