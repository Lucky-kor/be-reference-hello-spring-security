package com.springboot.config;

import com.springboot.member.InMemoryMemberService;
import com.springboot.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * InMemory User 학습용
 */
@Profile("in-memory-user")
@Configuration
public class JavaConfigurationV1 {
    @Bean
    public MemberService inMemoryMemberService(UserDetailsManager userDetailsManager,
                                               PasswordEncoder passwordEncoder) {
        return new InMemoryMemberService(userDetailsManager, passwordEncoder);
    }
}
