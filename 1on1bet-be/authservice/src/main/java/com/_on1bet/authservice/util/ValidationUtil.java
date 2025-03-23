package com._on1bet.authservice.util;

import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidationUtil {

    public boolean validMobileNumber(String mobileNo, String countryCode) throws NumberParseException {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(mobileNo, countryCode);
            return phoneUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            log.error("Invalid mobile number or country code", e);
            return false;
        }
    }

}
