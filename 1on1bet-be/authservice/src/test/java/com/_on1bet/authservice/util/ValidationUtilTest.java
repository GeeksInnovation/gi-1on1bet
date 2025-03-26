package com._on1bet.authservice.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.i18n.phonenumbers.NumberParseException;

import reactor.test.StepVerifier;

@SpringBootTest
class ValidationUtilTest {

    @Autowired
    private ValidationUtil validationUtil;

    @Test
    void validMobileNumberAndCountryCode() throws NumberParseException {

        String mobileNo = "9876543210";
        String countryCode = "IN";
        StepVerifier.create(validationUtil.validMobileNumber(mobileNo, countryCode))
        .expectNext(true)
        .verifyComplete();
    }

    @Test
    void inValidMobileNumberAndCountryCode() throws NumberParseException {

        String mobileNo = "9876543210";
        String countryCode = "US";
        StepVerifier.create(validationUtil.validMobileNumber(mobileNo, countryCode))
        .expectNext(false)
        .verifyComplete();

    }
}
