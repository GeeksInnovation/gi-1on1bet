package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.service.RegisterService;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("auth/v1/register/")
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("verify/mobileno")
    public String firstTimeMobileNoRegisterHandler(@RequestParam("mobileno") @Nonnull Long mobileNo,
            @RequestParam("countrycode") @Nonnull Integer countrycodeid) {
        log.info("First time mobile number registration handler starts");
        // return registerService.
        return "Working fine!";
    }

}
