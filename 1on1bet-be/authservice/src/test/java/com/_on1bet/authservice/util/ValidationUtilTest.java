package com._on1bet.authservice.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.i18n.phonenumbers.NumberParseException;

@SpringBootTest
public class ValidationUtilTest {
    
    @Autowired
    private ValidationUtil validationUtil;

    @Test
    public void validMobileNumberAndCountryCode() throws NumberParseException {

        String mobileNo = "9876543210";
        String countryCode = "IN";
        assertTrue(validationUtil.validMobileNumber(mobileNo, countryCode));

    }

    @Test
    public void inValidMobileNumberAndCountryCode() throws NumberParseException {

        String mobileNo = "9876543210";
        String countryCode = "US";
        assertFalse(validationUtil.validMobileNumber(mobileNo, countryCode));

    }
}
