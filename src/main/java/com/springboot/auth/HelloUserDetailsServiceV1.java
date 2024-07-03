package com.springboot.auth;

import com.springboot.auth.utils.HelloAuthorityUtils;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.Member;
import com.springboot.member.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;

/**
 * - Custom UserDetails 사용 안함
 * - HelloAuthorityUtils를 바로 사용(정적인 방식)하여 Spring Security에 Role 정보 제공
 */
//@Component
public class HelloUserDetailsServiceV1 implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final HelloAuthorityUtils authorityUtils;

    public HelloUserDetailsServiceV1(MemberRepository memberRepository, HelloAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Collection<? extends GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());

        return new User(findMember.getEmail(), findMember.getPassword(), authorities);
    }
}
