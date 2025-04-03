package com._on1bet.authservice.service;

import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;
import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;
import static com._on1bet.authservice.util.Constants.ERR_MSG_USER_NOT_AVAILABLE;
import static com._on1bet.authservice.util.Constants.ERR_MSG_INVALID_OTP;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com._on1bet.authservice.entity.ProfileDetails;
import com._on1bet.authservice.entity.UserDetails;
import com._on1bet.authservice.exception.CountryCodeOrMobileNumberInvalidException;
import com._on1bet.authservice.exception.InvalidOTPException;
import com._on1bet.authservice.exception.UserAldreadyExitException;
import com._on1bet.authservice.exception.UserNotAvailableException;
import com._on1bet.authservice.model.request.RegsiterUserRequest;
import com._on1bet.authservice.model.request.SaveProfileDetailsRequest;
import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.model.response.RegisterUserResponse;
import com._on1bet.authservice.model.response.SaveProfileDetailsResponse;
import com._on1bet.authservice.repo.ProfileDetailsRepo;
import com._on1bet.authservice.repo.UserDetailsRepo;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.util.RedisService;
import com._on1bet.authservice.util.UserIDGenerator;
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
    private final PasswordEncoder passwordEncoder;
    private final ProfileDetailsRepo profileDetailsRepo;

    public RegisterService(ValidationUtil validationUtil, _on1BetResponseBuilder _on1betResponseBuilder,
            UserDetailsRepo userDetailsRepo, RedisService redisService, UtilRepo utilRepo,
            PasswordEncoder passwordEncoder, ProfileDetailsRepo profileDetailsRepo) {
        this.validationUtil = validationUtil;
        this._on1betResponseBuilder = _on1betResponseBuilder;
        this.userDetailsRepo = userDetailsRepo;
        this.redisService = redisService;
        this.utilRepo = utilRepo;
        this.passwordEncoder = passwordEncoder;
        this.profileDetailsRepo = profileDetailsRepo;
    }

    public Mono<_on1BetResponse<OTPResponse>> generateOTP(Long mobileNo, Integer countryCode) {
        return extractCountryCode(countryCode)
                .flatMap(isoCode -> validateMobileNumber(mobileNo, isoCode))
                .flatMap(valid -> checkUserExists(mobileNo))
                .flatMap(valid -> generateOtp(mobileNo))
                .onErrorResume(this::handleError);
    }

    public Mono<_on1BetResponse<RegisterUserResponse>> registerUser(RegsiterUserRequest regsiterUserRequest) {
        return validateOTP(regsiterUserRequest.getMobileNo(), regsiterUserRequest.getEnteredOTP())
                .flatMap(isValid -> isValid ? saveUser(regsiterUserRequest)
                        : Mono.error(new InvalidOTPException(ERR_MSG_INVALID_OTP)))
                .map(userId -> RegisterUserResponse.builder().userId(userId).build())
                .map(registerUserResponse -> _on1betResponseBuilder.buildSuccessResponse(registerUserResponse))
                .onErrorResume(this::handleError);
    }

    public Mono<_on1BetResponse<SaveProfileDetailsResponse>> saveProfileDetails(
            SaveProfileDetailsRequest saveProfileDetailsRequest) {

        return userDetailsRepo.existsById(saveProfileDetailsRequest.getUserId())
                .filter(userDetails -> userDetails)
                .flatMap(userDetails -> profileDetailsRepo.save(buildProfileDetails(saveProfileDetailsRequest)))
                .map(savedProfileDetails -> {
                    return _on1betResponseBuilder.buildSuccessResponse(
                            SaveProfileDetailsResponse.builder().profileId(savedProfileDetails.getId()).build());
                })
                .switchIfEmpty(Mono.error(new UserNotAvailableException(ERR_MSG_USER_NOT_AVAILABLE)))
                .onErrorResume(this::handleError);
    }

    private Mono<Boolean> validateOTP(Long mobileNo, String enteredOTP) {
        return redisService.validateOTP(mobileNo, enteredOTP);
    }

    private Mono<String> saveUser(RegsiterUserRequest regsiterUserRequest) {
        String userId = UserIDGenerator.generateUserId();
        UserDetails userDetails = UserDetails.builder()
                .mobileNo(regsiterUserRequest.getMobileNo().toString()).userId(userId)
                .countryCodeDetails(regsiterUserRequest.getCountryCode()).build();
        return userDetailsRepo.save(userDetails).map(UserDetails::getUserId);
    }

    private Mono<String> extractCountryCode(Integer countryCode) {
        return utilRepo.getIsoCodeFromId(countryCode).filter(StringUtils::hasText).switchIfEmpty(
                Mono.error(new CountryCodeOrMobileNumberInvalidException(ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID)));
    }

    private Mono<Boolean> validateMobileNumber(Long mobileNo, String isoCode) {
        return validationUtil.validMobileNumber(mobileNo.toString(), isoCode)
                .filter(valid -> valid)
                .switchIfEmpty(Mono.error(
                        new CountryCodeOrMobileNumberInvalidException(ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID)));
    }

    private Mono<Boolean> checkUserExists(Long mobileNo) {
        return userDetailsRepo.existsByMobileNo(mobileNo.toString()).filter(isUserExit -> !isUserExit)
                .switchIfEmpty(Mono.error(new UserAldreadyExitException(ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS)));

    }

    private Mono<_on1BetResponse<OTPResponse>> generateOtp(Long mobileNo) {
        return redisService.generateAndStoreOTP(mobileNo)
                .map(otp -> _on1betResponseBuilder.buildSuccessResponse(OTPResponse.builder().otp(otp).build()));
    }

    private ProfileDetails buildProfileDetails(SaveProfileDetailsRequest saveProfileDetailsRequest) {
        return ProfileDetails.builder()
                .userId(saveProfileDetailsRequest.getUserId())
                .emailId(saveProfileDetailsRequest.getEmailId())
                .firstName(saveProfileDetailsRequest.getFirstName())
                .lastName(saveProfileDetailsRequest.getLastName())
                .password(passwordEncoder.encode(saveProfileDetailsRequest.getPassword()))
                .build();
    }

    private <T> Mono<_on1BetResponse<T>> handleError(Throwable ex) {
        if (ex instanceof UserAldreadyExitException || ex instanceof CountryCodeOrMobileNumberInvalidException
                || ex instanceof InvalidOTPException || ex instanceof UserNotAvailableException) {
            return Mono.just(_on1betResponseBuilder.buildFailureResponse(ex.getMessage()));
        }
        return Mono.error(ex);
    }
}
