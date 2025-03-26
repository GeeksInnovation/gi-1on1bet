package com._on1bet.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com._on1bet.authservice.exception.CountryCodeOrMobileNumberInvalidException;
import com._on1bet.authservice.model.response.OTPResponse;
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
import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;

@SpringBootTest
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
  void testGenerateOTP_Success() {

    Long mobileNo = 9876543210L;
    Integer countryCode = 55;
    String isoCode = ISO_CODE_IN;
    String generatedOtp = "123456";

    when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(isoCode));
    when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(Mono.just(true));
    when(userDetailsRepo.existsById(mobileNo)).thenReturn(false);
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
  void testGenerateOTP_Failure_InvalidCountryCodeOrMobileNumber() {

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
  void testGenerateOTP_Failure_InvalidCountryCodeOrMobileNumber_through_Google_i18n() {
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
  void testGenerateOTP_Failure_UserAldreadyExits() {

    Long mobileNo = 9876543210L;
    Integer countryCode = 55;
    String isoCode = ISO_CODE_IN;

    when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(isoCode));
    when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(Mono.just(true));
    when(userDetailsRepo.existsById(mobileNo)).thenReturn(true);

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

}
