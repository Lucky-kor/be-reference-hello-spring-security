package com.springboot.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

//@Component
public class HelloUserAuthenticationProvider implements AuthenticationProvider {
    private final HelloUserDetailsServiceV3 userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public HelloUserAuthenticationProvider(HelloUserDetailsServiceV3 userDetailsService,
                                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
//
//        String username = authToken.getName();
//        Optional.ofNullable(username).orElseThrow(() -> new UsernameNotFoundException("Invalid User name or User Password"));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        String password = userDetails.getPassword();
//        verifyCredentials(authToken.getCredentials(), password);
//
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
//        return UsernamePasswordAuthenticationToken.authenticated(username, password, authorities);
//    }

    // V2: AuthenticationException을 rethrow 하는 개선 코드
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

        String username = authToken.getName();
        Optional.ofNullable(username).orElseThrow(() -> new UsernameNotFoundException("Invalid User name or User Password"));
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String password = userDetails.getPassword();
            verifyCredentials(authToken.getCredentials(), password);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return UsernamePasswordAuthenticationToken.authenticated(username, password, authorities);
        } catch (Exception ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }

    // HelloUserAuthenticationProvider가 Username/Password 방식의 인증을 지원한다는 것을 Spring Security에게 알려준다.
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    private void verifyCredentials(Object credentials, String password) {
        if (!passwordEncoder.matches((String)credentials, password)) {
            throw new BadCredentialsException("Invalid User name or User Password");
        }
    }
}
