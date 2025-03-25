// package com._on1bet.authservice.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.mockito.Mockito.when;

// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.boot.test.context.SpringBootTest;

// import com._on1bet.authservice.model.response.OTPResponse;
// import com._on1bet.authservice.repo.UserDetailsRepo;
// import com._on1bet.authservice.repo.UtilRepo;
// import com._on1bet.authservice.util.RedisService;
// import com._on1bet.authservice.util.ValidationUtil;
// import com._on1betutils.utils1on1bet._on1BetResponse;
// import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// import static com._on1bet.authservice.util.Constants.ISO_CODE_IN;
// import static com._on1bet.authservice.util.Constants.ISO_CODE_US;
// import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;
// import static com._on1bet.authservice.util.Constants.ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;

// @SpringBootTest
// class RegisterServiceTest {

//   @InjectMocks
//   RegisterService registerService;

//   @Mock
//   private ValidationUtil validationUtil;

//   @Mock
//   private _on1BetResponseBuilder _on1betResponseBuilder;

//   @Mock
//   private UserDetailsRepo userDetailsRepo;

//   @Mock
//   private RedisService redisService;

//   @Mock
//   private UtilRepo utilRepo;

//   @Test
//   void testExtractCountryCodeWithValidValues() {
//     Integer countryCode = 55;
//     when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(Mono.just(ISO_CODE_IN));

//     StepVerifier.create(registerService.(countryCode))
//         .expectNext(ISO_CODE_IN)
//         .verifyComplete();
//   }

//   @Test
//   void testExtractCountryCodeWithInvalidValues() {
//     Integer countryCode = 9999;
//     when(utilRepo.getIsoCodeFromId(countryCode)).thenReturn(null);
//     assertNull(registerService.extractCountryCode(countryCode));
//   }

//   @Test
//   void testgenerateOTPSuccess() {

//     Long mobileNo = 9876543210L;
//     Integer countryCode = 55;
//     String isoCode = ISO_CODE_IN;
//     String generatedOTP = "123456";

//     when(registerService.extractCountryCode(countryCode)).thenReturn(isoCode);
//     when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(true);
//     when(userDetailsRepo.existsById(mobileNo)).thenReturn(false);
//     when(redisService.generateAndStoreOTP(mobileNo)).thenReturn(generatedOTP);

//     when(_on1betResponseBuilder.buildSuccessResponse(Mockito.any()))
//         .thenReturn(new _on1BetResponse<>(true, null, new OTPResponse(generatedOTP)));

//     _on1BetResponse<OTPResponse> actual = registerService.generateOTP(mobileNo, countryCode);

//     assertNotNull(actual);
//     assertEquals(true, actual.getServiceStatus());
//     assertEquals("123456", actual.getData().getOtp());

//   }

//   @Test
//   void testgenerateOTPFailureWithInvalidMobileNoOrCountryCode() {

//     Long mobileNo = 9876543210L;
//     Integer countryCode = 55;
//     String isoCode = ISO_CODE_US;
//     String errorMsg = ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;

//     when(registerService.extractCountryCode(countryCode)).thenReturn(isoCode);
//     when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(false);

//     when(_on1betResponseBuilder.buildFailureResponse(Mockito.any()))
//         .thenReturn(new _on1BetResponse<>(false, errorMsg, null));

//     _on1BetResponse<OTPResponse> actual = registerService.generateOTP(mobileNo, countryCode);

//     assertNotNull(actual);
//     assertEquals(false, actual.getServiceStatus());
//     assertEquals(errorMsg, actual.getServiceMessage());

//   }

//   @Test
//   void testgenerateOTPFailureWhenCountryCodeisNotInDB() {

//     Long mobileNo = 9876543210L;
//     Integer countryCode = 999;
//     String errorMsg = ERR_MSG_MOBILE_NUMBER_COUNTRY_CODE_INVALID;

//     when(registerService.extractCountryCode(countryCode)).thenReturn(null);
//     when(_on1betResponseBuilder.buildFailureResponse(Mockito.any()))
//         .thenReturn(new _on1BetResponse<>(false, errorMsg, null));

//     _on1BetResponse<OTPResponse> actual = registerService.generateOTP(mobileNo, countryCode);

//     assertNotNull(actual);
//     assertEquals(false, actual.getServiceStatus());
//     assertEquals(errorMsg, actual.getServiceMessage());

//   }

//   @Test
//   void testgenerateOTPFailureIfMobileNumberAldreadyExits() {

//     Long mobileNo = 9876543210L;
//     Integer countryCode = 55;
//     String isoCode = ISO_CODE_IN;
//     String errorMsg = ERR_MSG_MOBILE_NUMBER_ALDREADY_EXITS;

//     when(registerService.extractCountryCode(countryCode)).thenReturn(isoCode);
//     when(validationUtil.validMobileNumber(mobileNo.toString(), isoCode)).thenReturn(true);
//     when(userDetailsRepo.existsById(mobileNo)).thenReturn(true);
//     when(_on1betResponseBuilder.buildFailureResponse(Mockito.any()))
//         .thenReturn(new _on1BetResponse<>(false, errorMsg, null));

//     _on1BetResponse<OTPResponse> actual = registerService.generateOTP(mobileNo, countryCode);

//     assertNotNull(actual);
//     assertEquals(false, actual.getServiceStatus());
//     assertEquals(errorMsg, actual.getServiceMessage());

//   }
// }
