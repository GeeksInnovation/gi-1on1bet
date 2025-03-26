package com._on1bet.authservice.util;

import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ValidationUtil {

    public Mono<Boolean> validMobileNumber(String mobileNo, String countryCode) {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(mobileNo, countryCode);
            return Mono.just(phoneUtil.isValidNumber(phoneNumber));
        } catch (NumberParseException e) {
            log.error("Invalid mobile number or country code", e);
            return Mono.just(false);
        }
    }

}
