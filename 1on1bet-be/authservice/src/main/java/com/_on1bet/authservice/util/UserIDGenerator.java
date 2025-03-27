package com._on1bet.authservice.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Component
public class UserIDGenerator {

    private static final String PREFIX = "1ON1";
    private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] DIGITS = "0123456789".toCharArray();
    private static final SecureRandom random = new SecureRandom();

    public static String generateUserId() {
        String part1 = NanoIdUtils.randomNanoId(random, LETTERS, 2);
        String part2 = NanoIdUtils.randomNanoId(random, DIGITS, 2);
        String part3 = NanoIdUtils.randomNanoId(random, LETTERS, 1);
        String part4 = NanoIdUtils.randomNanoId(random, DIGITS, 1);

        return PREFIX + part1 + part2 + part3 + part4;
    }

}
