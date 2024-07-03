package com.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PasswordEncoderTest {
    @Test
    public void encodePasswordTest() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        String message = "hello";

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        String bcryptPassword = passwordEncoder.encode(message);
        System.out.println("bcrypt encoded: " + bcryptPassword);

        passwordEncoder = new DelegatingPasswordEncoder("noop", encoders);
        String noopPassword = passwordEncoder.encode(message);
        System.out.println("noop encoded: " + noopPassword);

        passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        String pbkdf2Password = passwordEncoder.encode(message);
        System.out.println("pbkdf2 encoded: " + pbkdf2Password);

        // scrypt의 경우, bouncycastle 라이브러리가 필요합니다. build.gradle 참고
        passwordEncoder = new DelegatingPasswordEncoder("scrypt", encoders);
        String scryptPassword = passwordEncoder.encode(message);
        System.out.println("scrypt encoded: " + scryptPassword);

        passwordEncoder = new DelegatingPasswordEncoder("sha256", encoders);
        String sha256Password = passwordEncoder.encode(message);
        System.out.println("sha256 encoded: " + sha256Password);

        assertThat(true, is(passwordEncoder.matches(message, bcryptPassword)));
        assertThat(true, is(passwordEncoder.matches(message, noopPassword)));
        assertThat(true, is(passwordEncoder.matches(message, pbkdf2Password)));
        assertThat(true, is(passwordEncoder.matches(message, scryptPassword)));
        assertThat(true, is(passwordEncoder.matches(message, sha256Password)));
    }
}
