package com._on1bet.authservice.service;

import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;
import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com._on1bet.authservice.exception.CountryCodeOrMobileNumberInvalidException;
import com._on1bet.authservice.exception.UserAldreadyExitException;
import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.repo.UserDetailsRepo;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.util.RedisService;
import com._on1bet.authservice.util.ValidationUtil;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;
import com._on1betutils.utils1on1bet._on1BetResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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

    public Mono<_on1BetResponse<OTPResponse>>  generateOTP(Long mobileNo, Integer countryCode) {

        return extractCountryCode(countryCode)
        .flatMap(isoCode -> validateMobileNumber(mobileNo, isoCode))
        .flatMap(valid -> checkUserExists(mobileNo))
        .flatMap(valid -> generateOtp(mobileNo))
        .onErrorResume(this::handleError);
    }

    private Mono<String> extractCountryCode(Integer countryCode) {
        return utilRepo.getIsoCodeFromId(countryCode).filter(StringUtils::hasText).switchIfEmpty(
                Mono.error(new CountryCodeOrMobileNumberInvalidException(ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID)));
    }

    private Mono<Boolean> validateMobileNumber(Long mobileNo, String isoCode) {
        return validationUtil.validMobileNumber(mobileNo.toString(), isoCode)
            ? Mono.just(true)
            : Mono.error(new CountryCodeOrMobileNumberInvalidException(ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID));
    }

    private Mono<Boolean> checkUserExists(Long mobileNo) {
        return userDetailsRepo.existsById(mobileNo)
            ? Mono.error(new UserAldreadyExitException(ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS))
            : Mono.just(true);
    }

    private Mono<_on1BetResponse<OTPResponse>> generateOtp(Long mobileNo) {
        return redisService.generateAndStoreOTP(mobileNo)
            .map(otp -> _on1betResponseBuilder.buildSuccessResponse(OTPResponse.builder().otp(otp).build()));
    }

    private Mono<_on1BetResponse<OTPResponse>> handleError(Throwable ex) {
        if (ex instanceof UserAldreadyExitException || ex instanceof CountryCodeOrMobileNumberInvalidException) {
            return Mono.just(_on1betResponseBuilder.buildFailureResponse(ex.getMessage()));
        }
        return Mono.error(ex);
    }
}
