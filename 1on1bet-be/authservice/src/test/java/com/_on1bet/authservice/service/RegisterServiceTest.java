package com._on1bet.authservice.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com._on1bet.authservice.repo.UserDetailsRepo;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1bet.authservice.util.RedisService;
import com._on1bet.authservice.util.ValidationUtil;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

@SpringBootTest
class RegisterServiceTest {

  @Autowired
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
    void testValidExtractCountryCode() {

        Integer countryCode = 55;
        assertNotNull(registerService.extractCountryCode(countryCode));
    }

}
