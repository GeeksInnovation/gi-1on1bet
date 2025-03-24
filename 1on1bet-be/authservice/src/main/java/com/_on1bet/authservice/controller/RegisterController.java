package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.service.RegisterService;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com.google.i18n.phonenumbers.NumberParseException;

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
    public _on1BetResponse<OTPResponse> generateOTP(@RequestParam("mobileno") @Nonnull Long mobileNo,
            @RequestParam("countrycode") @Nonnull Integer countrycodeid) throws NumberParseException {
        log.info("Validation and generating otp handler starts");
        return registerService.generateOTP(mobileNo, countrycodeid);  
    }

}
