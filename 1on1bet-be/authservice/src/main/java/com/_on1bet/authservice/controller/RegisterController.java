package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.model.request.RegsiterUserRequest;
import com._on1bet.authservice.model.request.SaveProfileDetailsRequest;
import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.model.response.RegisterUserResponse;
import com._on1bet.authservice.service.RegisterService;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com.google.i18n.phonenumbers.NumberParseException;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/register/")
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("verify/mobileno")
    public Mono<_on1BetResponse<OTPResponse>> generateOTPHandler(@RequestParam("mobileno") @Nonnull Long mobileNo,
        @RequestParam("countrycode") @Nonnull Integer countrycodeid) throws NumberParseException {
        log.info("Validation and generating otp handler starts");
        return registerService.generateOTP(mobileNo, countrycodeid);
    }

    @PostMapping("save/user")
    public Mono<_on1BetResponse<RegisterUserResponse>> saveUserHandler(
        @RequestBody RegsiterUserRequest userDetailsRequest) {
        log.info("Save user handler starts");
        return registerService.registerUser(userDetailsRequest);
    }

    @PostMapping("profile/save/user")
    public String postMethodName(@RequestBody SaveProfileDetailsRequest saveProfileDetailsRequest) {
        log.info("Save profile details for user handler starts");
        return registerService.saveProfileDetails(saveProfileDetailsRequest);
    }
}