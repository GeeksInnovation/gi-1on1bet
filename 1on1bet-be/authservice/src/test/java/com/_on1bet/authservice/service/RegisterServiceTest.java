package com._on1bet.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com._on1bet.authservice.entity.UserDetails;
import com._on1bet.authservice.exception.CountryCodeOrMobileNumberInvalidException;
import com._on1bet.authservice.model.request.RegsiterUserRequest;
import com._on1bet.authservice.model.response.OTPResponse;
import com._on1bet.authservice.model.response.RegisterUserResponse;
import com._on1bet.authservice.repo.UserDetailsRepo;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.util.RedisService;
import com._on1bet.authservice.util.ValidationUtil;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com._on1bet.authservice.util.Constants.ISO_CODE_IN;
import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;
import static com._on1bet.authservice.util.Constants.INVALID_OTP;
import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

  @InjectMocks
  RegisterService registerService;

  @Mock
  private ValidationUtil validationUtil;

  @Mock
  private _on1BetResponseBuilder _on1betResponseBuilder;

  @Mock
  private UserDetailsRepo userDetailsRepo;

  @Mock
  private RedisService redisService;

  @Mock
  private UtilRepo utilRepo;

  @Test
  void test_generateOTP_Success() {

    Long mobileNo = 9876543210L;
    Integer countryCode = 55;
    String isoCode = ISO_CODE_IN;
    String generatedOtp = "123456";

    when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(isoCode));
    when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(Mono.just(true));
    when(userDetailsRepo.existsById(mobileNo)).thenReturn(Mono.just(false));
    when(redisService.generateAndStoreOTP(mobileNo)).thenReturn(Mono.just(generatedOtp));

    _on1BetResponse<OTPResponse> successResponse = new _on1BetResponse<>(true, null, new OTPResponse(generatedOtp));
    when(_on1betResponseBuilder.buildSuccessResponse(any(OTPResponse.class)))
        .thenReturn(successResponse);

    StepVerifier.create(registerService.generateOTP(mobileNo, countryCode))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(true, response.getServiceStatus());
          assertEquals(generatedOtp, response.getData().getOtp());
        })
        .verifyComplete();

  }

  @Test
  void test_generateOTP_Failure_InvalidCountryCodeOrMobileNumber() {

    Long mobileNo = 9876543210L;
    Integer countryCode = 99999;

    when(utilRepo.getIsoCodeFromId(countryCode))
        .thenReturn(
            Mono.error(new CountryCodeOrMobileNumberInvalidException(ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID)));

    _on1BetResponse<Object> failureResponse = new _on1BetResponse<>(false,
        ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID, null);
    when(_on1betResponseBuilder.buildFailureResponse(any(String.class)))
        .thenReturn(failureResponse);

    StepVerifier.create(registerService.generateOTP(mobileNo, countryCode))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(false, response.getServiceStatus());
        })
        .verifyComplete();
  }

  @Test
  void test_generateOTP_Failure_InvalidCountryCodeOrMobileNumber_through_Google_i18n() {
    Long mobileNo = 9876543210L;
    Integer countryCode = 55;
    String isoCode = ISO_CODE_IN;

    when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(isoCode));
    when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(Mono.just(false));

    _on1BetResponse<Object> failureResponse = new _on1BetResponse<>(false,
        ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID, null);

    when(_on1betResponseBuilder.buildFailureResponse(any(String.class)))
        .thenReturn(failureResponse);

    StepVerifier.create(registerService.generateOTP(mobileNo, countryCode))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(false, response.getServiceStatus());
        })
        .verifyComplete();
  }

  @Test
  void test_generateOTP_Failure_UserAldreadyExits() {

    Long mobileNo = 9876543210L;
    Integer countryCode = 55;
    String isoCode = ISO_CODE_IN;

    when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(isoCode));
    when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(Mono.just(true));
    when(userDetailsRepo.existsById(mobileNo)).thenReturn(Mono.just(true));

    _on1BetResponse<Object> failureResponse = new _on1BetResponse<>(false,
        ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS, null);

    when(_on1betResponseBuilder.buildFailureResponse(any(String.class)))
        .thenReturn(failureResponse);

    StepVerifier.create(registerService.generateOTP(mobileNo, countryCode))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(false, response.getServiceStatus());
        })
        .verifyComplete();

  }

  @Test
  void test_registerUser_Success() {

    String userId = "1ON1HY78Y9";
    _on1BetResponse<Object> successResponse = new _on1BetResponse<Object>(true, null,
        RegisterUserResponse.builder().userId(userId).build());

    UserDetails userDetails = UserDetails.builder().userId(userId).build();

    RegsiterUserRequest regsiterUserRequest = RegsiterUserRequest.builder().countryCode(55).enteredOTP("123456")
        .mobileNo(9876543210L).build();

    when(redisService.validateOTP(any(), any())).thenReturn(Mono.just(true));
    when(userDetailsRepo.save(any())).thenReturn(Mono.just(userDetails));
    when(_on1betResponseBuilder.buildSuccessResponse(any())).thenReturn(successResponse);

    StepVerifier.create(registerService.registerUser(regsiterUserRequest))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(true, response.getServiceStatus());
          assertEquals(userId, response.getData().getUserId());
        })
        .verifyComplete();
  }

  @Test
  void test_verifyOTP_Failure() {

    _on1BetResponse<Object> failureResponse = new _on1BetResponse<Object>(false, INVALID_OTP,
        null);
    RegsiterUserRequest regsiterUserRequest = RegsiterUserRequest.builder().countryCode(55).enteredOTP("123456")
        .mobileNo(9876543210L).build();

    when(redisService.validateOTP(any(), any())).thenReturn(Mono.just(false));
    when(_on1betResponseBuilder.buildFailureResponse(any(String.class)))
        .thenReturn(failureResponse);

    StepVerifier.create(registerService.registerUser(regsiterUserRequest))
        .assertNext(response -> {
          assertNotNull(response);
          assertEquals(false, response.getServiceStatus());
        })
        .verifyComplete();
  }

}
