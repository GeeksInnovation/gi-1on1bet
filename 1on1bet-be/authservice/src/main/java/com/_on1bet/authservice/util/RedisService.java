package com._on1bet.authservice.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

@Component
@Slf4j
public class RedisService {


    private final RedisTemplate<String,String> redisTemplate;
    private static final long OTP_EXPIRY_TIME = 5; 
    private static final Random random = new Random();

    public RedisService(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateAndStoreOTP (Long mobileNo) {

        String otp = String.format("%06d", (100000 + random.nextInt(900000)));
        log.info("OTP created and stored for 1 minute");
        redisTemplate.opsForValue().set(mobileNo.toString(), otp , OTP_EXPIRY_TIME, TimeUnit.MINUTES);
        return otp;
    }
    
}
