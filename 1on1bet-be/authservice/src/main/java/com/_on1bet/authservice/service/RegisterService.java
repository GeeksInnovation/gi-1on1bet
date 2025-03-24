package com._on1bet.authservice.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.repo.UserDetailsRepo;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.util.RedisService;
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
    private final UserDetailsRepo userDetailsRepo;
    private final RedisService redisService;
    private final UtilRepo utilRepo;

    public RegisterService(ValidationUtil validationUtil, _on1BetResponseBuilder _on1betResponseBuilder,
            UserDetailsRepo userDetailsRepo, RedisService redisService, UtilRepo utilRepo) {
        this.validationUtil = validationUtil;
        this._on1betResponseBuilder = _on1betResponseBuilder;
        this.userDetailsRepo = userDetailsRepo;
        this.redisService = redisService;
        this.utilRepo = utilRepo;
    }

    public _on1BetResponse<OTPResponse> generateOTP(Long mobileNo, Integer countryCode) throws NumberParseException {

        log.info("Cheking is mobile number and country is valid {} {}", mobileNo, countryCode);
        String isoCode = extractCountryCode(countryCode);

        if (!StringUtils.hasText(isoCode)) {
            return _on1betResponseBuilder.buildFailureResponse("Mobile number or country code is invalid");
        }

        boolean isMobileNoValid = validationUtil.validMobileNumber(mobileNo.toString(), isoCode);
        if (!isMobileNoValid) {
            return _on1betResponseBuilder.buildFailureResponse("Mobile number or country code is invalid");
        }

        log.info("Mobile number and country code is valid, checking user aldready exists or not in DB");
        boolean isUserExits = userDetailsRepo.existsById(mobileNo);
        if (isUserExits) {
            log.info("Mobile number {} aldready exists", mobileNo);
            return _on1betResponseBuilder.buildFailureResponse("Mobile number aldready exits");
        }

        log.info("Generating OTP for mobile number {}", mobileNo);
        String generatedOTP = redisService.generateAndStoreOTP(mobileNo);
        return _on1betResponseBuilder.buildSuccessResponse(OTPResponse.builder().otp(generatedOTP).build());
    }

    public String extractCountryCode(Integer countryCode) {
        return utilRepo.getIsoCodeFromId(countryCode);
    }
}
