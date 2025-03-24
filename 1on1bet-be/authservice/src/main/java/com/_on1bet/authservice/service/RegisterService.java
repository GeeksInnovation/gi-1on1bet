package com._on1bet.authservice.service;

import org.springframework.stereotype.Service;

import com._on1bet.authservice.util.ValidationUtil;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com.google.i18n.phonenumbers.NumberParseException;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RegisterService { 

    private final ValidationUtil validationUtil;
    private final _on1BetResponseBuilder _on1betResponseBuilder;

    public RegisterService(ValidationUtil validationUtil, _on1BetResponseBuilder _on1betResponseBuilder) {
        this.validationUtil = validationUtil;
        this._on1betResponseBuilder = _on1betResponseBuilder;
    }

    public _on1BetResponse<String> registerMobileNumer(String mobileNo, String countryCode) throws NumberParseException {
       log.info("Cheking is mobile number and country is valid {} {}", mobileNo,countryCode);
       boolean isMobileNoValid = validationUtil.validMobileNumber(mobileNo, countryCode);
       if(isMobileNoValid) {
          log.info("Mobile number and country code is valid, checking user aldready exists or not");
        
       }
       return _on1betResponseBuilder.buildFailureResponse("Mobile number or country code is invalid");
    }

    // public Boolean isUserExists(String mobileNo) {
    // change in one line one line test test just testing
    // }
}
