package com.springboot.config;

import com.springboot.auth.utils.HelloAuthorityUtils;
import com.springboot.member.DBMemberServiceV1;
import com.springboot.member.DBMemberServiceV2;
import com.springboot.member.MemberRepository;
import com.springboot.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 데이터베이스 연동을 통한 Spring Security 학습용
 */
@Profile("db-user")
@Configuration
public class JavaConfigurationV2 {
    // Role은 메모리에서 관리
    @Bean
    public MemberService dbMemberServiceV1(MemberRepository memberRepository,
                                         PasswordEncoder passwordEncoder) {
        return new DBMemberServiceV1(memberRepository, passwordEncoder);
    }

    // Role까지 DB에서 관리
    @Primary
    @Bean
    public MemberService dbMemberServiceV2(MemberRepository memberRepository,
                                           PasswordEncoder passwordEncoder,
                                           HelloAuthorityUtils authorityUtils) {
        return new DBMemberServiceV2(memberRepository, passwordEncoder, authorityUtils);
    }
}
